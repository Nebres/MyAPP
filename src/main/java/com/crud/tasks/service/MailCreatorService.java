package com.crud.tasks.service;

import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private CompanyConfig companyConfig;

    public String buildTrelloCardEMail(String message) {
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
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

}
