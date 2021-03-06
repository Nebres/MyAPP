package com.crud.tasks.service;

import com.crud.tasks.config.CompanyConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class TrelloService {

    private final String SUBJECT = "Tasks: New Trello card";

    @Autowired
    TrelloClient trelloClient;

    @Autowired
    EmailService emailService;

    @Autowired
    CompanyConfig companyConfig;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return Optional.ofNullable(trelloClient.getTrelloBoards()).orElse(Collections.emptyList());
    }

    public CreatedTrelloCardDto createTrelloCard(final TrelloCardDto trelloCardDto) {

        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);
        ofNullable(newCard).ifPresent(card -> emailService.sendMessageForNewTrelloCard(
                (new Mail(companyConfig.getAdminMail(), null, SUBJECT, "New Card: " +
                trelloCardDto.getName() + " has been created on your Trello account"))));

        return newCard;
    }

}
