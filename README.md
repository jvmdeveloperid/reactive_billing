# reactive_billing
this is sample code how to transactional using asynchronous communication.. 

  * t0 - [Java8] - newest java with lambda and stream
  * t1 - [Maven] - for dependencies management
  * t2 - [Spring Boot - Spring data] - framework used
  * t3 - [Axon Framework] - CQRS
  * t4 - [Jackson] - for json serialization / deserealization
  * t5 - [Lombok] - for java source generator
  * t6 - [Vavr] - java functional library
  
  # Packaging Structure of Module:
  ## Config
  > Config module is used to store all class for common utility and spring configuration
  
  ## common
  > commontest module is used to store all class for test utility
  
  ## API
  > API module is used to store class that is likely to be used by other modules for integration. Examples classes included in the API is an event, command, or DTO
  
  ## Domain
  > Module that holds the core logic of the module, can be broken down into two sub module write / read if built in based CQRS paradigm. Sub Query module consists of logic view that populate based event handling. Sub write Module is composed of a command handler, service (if any), and aggregates. Domain Write usually in-dependency with the domain query.
  
  ## Infrastructure
  > package that accommodates infrastructure code such as rest endpoint, WebSocket endpoint, as well as other endpoint. Integrated with write domain by sending commands via the command bus.