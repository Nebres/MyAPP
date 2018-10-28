package com.crud.tasks.service;

import com.crud.tasks.config.CompanyConfig;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    TaskRepository taskRepository;

    public String buildTrelloCardEMail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can menage your tasks");
        functionality. add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http//loclahost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", companyConfig.getAdminName());
        context.setVariable("company_name", companyConfig.getCompanyName());
        context.setVariable("company_address", companyConfig.getCompanyStreet());
        context.setVariable("company_address_number", companyConfig.getCompanyAddressNumber());
        context.setVariable("company_phone", companyConfig.getCompanyPhone());
        context.setVariable("goodbye_message", "Have a nice day");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("company_config", companyConfig);
        context.setVariable("application_functionality", functionality);

        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildSchedulerInfoEMail(String message) {

        boolean isTasksInDb = taskRepository.count() > 0;
        boolean isFriend = companyConfig.getAdminName().startsWith("Kodilla");
        List<String> tasks = taskRepository.findAll().stream().map(Task::getTitle).collect(Collectors.toList());

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http//loclahost:8888/crud");
        context.setVariable("button", "Go to Application");
        context.setVariable("preview", "Email about number of tasks");
        context.setVariable("admin_name", companyConfig.getAdminName());
        context.setVariable("company_name", companyConfig.getCompanyName());
        context.setVariable("company_address", companyConfig.getCompanyStreet());
        context.setVariable("company_address_number", companyConfig.getCompanyAddressNumber());
        context.setVariable("company_phone", companyConfig.getCompanyPhone());
        context.setVariable("goodbye_message", "Have a nice day");
        context.setVariable("show_button",  isTasksInDb);
        context.setVariable("is_friend", isFriend);
        context.setVariable("company_config", companyConfig);
        context.setVariable("tasks_in_db", tasks);

        return templateEngine.process("created-scheduler-info-mail", context);
    }

}
