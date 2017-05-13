package com.andipangeran.reactive.billing.infra.rest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import org.apache.http.HttpStatus;
import org.axonframework.eventhandling.EventBus;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static com.andipangeran.reactive.billing.infra.rest.JacksonTestUtil.defaultObjectMapper;
import static com.andipangeran.reactive.billing.infra.rest.JacksonTestUtil.writeValueAsString;
import static com.andipangeran.reactive.billing.infra.rest.RestConstant.V1_0;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {WebAppTest.class})
@ActiveProfiles("integration")
public abstract class IntegrationRest {

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected DataSource dataSource;

    @Autowired
    protected EventBus eventBus;


    @Value("${server.port}")
    private int serverPort;

    @Before
    public void tearUp() {
        RestAssuredMockMvc.webAppContextSetup(context);
        RestAssured.port = serverPort;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.config = RestAssured
            .config()
            .objectMapperConfig(objectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> defaultObjectMapper()));
    }

    @After
    public void tearDown() {
        RestAssuredMockMvc.reset();
    }

    public String getContentType() {
        return V1_0;
    }

    public void waitFor(Callable<Boolean> check, long timeout, TimeUnit unit) {

        await()
            .atMost(timeout, unit)
            .until(check);
    }

    public void waitFor(Callable<Boolean> check) {

        waitFor(check, 20, TimeUnit.SECONDS);
    }

    public <T> T getAPI(String path, Class<T> valueType) {
        return given()
            .log().all()
            .contentType(getContentType())
            .when()
            .get(path)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().body().as(valueType);
    }

    public <T> T getAPI(String path, Map<String, ?> params, Class<T> valueType) {
        return given()
            .log().all()
            .contentType(getContentType())
            .parameters(params)
            .when()
            .get(path)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().body().as(valueType);
    }

    public <T> T postAPI(String path, Object payload, Class<T> valueType) {
        return given()
            .log().all()
            .contentType(getContentType())
            .body(writeValueAsString(payload))
            .when()
            .post(path)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().body().as(valueType);
    }

    public void postAPI(String path, Object payload) {

        given()
            .log().all()
            .contentType(getContentType())
            .body(writeValueAsString(payload))
            .when()
            .post(path)
            .then()
            .statusCode(HttpStatus.SC_OK);
    }

    public void postFailAPI(String path, Object payload, int expectedStatusCode, String statusMessage) {

        given()
            .log().all()
            .contentType(getContentType())
            .body(writeValueAsString(payload))
            .when()
            .post(path)
            .then()
            .statusCode(expectedStatusCode)
            .body("status", equalTo(statusMessage));
    }
}
