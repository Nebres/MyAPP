//package com.crud.tasks.service;
//
//import com.crud.tasks.domain.Mail;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class SimpleEmailServiceTest {
//
//    @InjectMocks
//    private SimpleEmailService emailService;
//
//    @Mock
//    private JavaMailSender javaMailSender;
//
//    @Test
//    public void shouldSendEmail() {
//        //Given
//        Mail mail = new Mail ("test@test.com", null, "test", "TEST");
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(mail.getMailTo());
//        mailMessage.setSubject(mail.getSubject());
//        mailMessage.setText(mail.getMessage());
//        //When
//        emailService.send(mail);
//        //Than
//        verify(javaMailSender, times(1)).send(mailMessage);
//    }
//
//}