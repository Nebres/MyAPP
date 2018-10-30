package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendSchedulerInfoEmail() {
        //Given
        Mail mail = new Mail("test@test.com", null, "test", "Test");
        //When
        emailService.sendSchedulerInfoMail(mail);
        //Than
        verify(javaMailSender, times(1)).send(any(MimeMessagePreparator.class));
    }

    @Test
    public void shouldSendMailAboutNewTrelloCard() {
        Mail mail = new Mail("test@test.com", null, "test", "Test");
        //When
        emailService.sendMessageForNewTrelloCard(mail);
        //Than
        verify(javaMailSender, times(1)).send(any(MimeMessagePreparator.class));
    }

}
