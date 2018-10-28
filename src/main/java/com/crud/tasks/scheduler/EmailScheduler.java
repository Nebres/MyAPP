package com.crud.tasks.scheduler;

import com.crud.tasks.config.CompanyConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private final String SUBJECT = "Tasks: Once a day email";

    @Autowired
    private SimpleEmailService emailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CompanyConfig companyConfig;

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        long size = taskRepository.count();
        String taskLastLetterMatcher = size <= 1 ? " Task." : " Tasks.";
        emailService.send(new Mail(companyConfig.getAdminMail(), null, SUBJECT,
                "Currently in DB you got " + size + taskLastLetterMatcher, true));
    }

}
