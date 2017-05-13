package com.andipangeran.reactive.billing.domain.mail;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
@Slf4j
@Service
@NoArgsConstructor
public class MailServiceImpl implements MailService {

    @Override
    public void sendMail(String to) {
        log.debug("send email to {}", to);
    }
}
