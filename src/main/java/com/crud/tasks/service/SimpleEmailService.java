package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    MailCreatorService mailCreatorService;

    @Autowired
    JavaMailSender javaMailSender;


    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setCc(mail.getMailToCC());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEMail(mail.getMessage()), true);
        };
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setCc(mail.getMailToCC());
        mailMessage.setSubject(mail.getMailTo());
        mailMessage.setText(mailCreatorService.buildTrelloCardEMail(mail.getMessage()));
        return mailMessage;
    }

    public void send(Mail mail) {

        LOGGER.info("Starting email preparation...");
        try {
            javaMailSender.send(createMimeMessage(mail));
            LOGGER.info("Email has been Sent");
        }catch (MailException e) {
            LOGGER.error("Process failed: ", e.getMessage(), e);
        }
    }

}
