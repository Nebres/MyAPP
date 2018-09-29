package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    JavaMailSender javaMailSender;

    public SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        if (StringUtils.isNoneBlank(mail.getMailToCC())) {
            mailMessage.setCc(mail.getMailToCC());
        }
        return mailMessage;
    }


    public void send(final Mail mail) {

        LOGGER.info("Starting email preparation...");
        try {
            SimpleMailMessage mailMessage = createMailMessage(mail);

            javaMailSender.send(mailMessage);
            LOGGER.info("Email has been Sent");
        }catch (MailException e) {
            LOGGER.error("Process failed: ", e.getMessage(), e);
        }
    }

}
