package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class TrelloFacade {

    private final static Logger LOGGER = LoggerFactory.getLogger(TrelloFacade.class);

    @Autowired
    TrelloService trelloService;

    @Autowired
    TrelloMapper trelloMapper;

    public List<TrelloBoard> fetchTrelloBoards() {

        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloService.fetchTrelloBoards());
        LOGGER.info("Starting filtering boards...");
        List<TrelloBoard> filteredBoards = trelloBoards.stream()
                .filter(trelloBoard -> !trelloBoard.getName().equalsIgnoreCase("test"))
                .collect(toList());
        LOGGER.info("Boards have been filtered. Current list size: " + filteredBoards.size());
        return filteredBoards;
    }

    public CreatedTrelloCardDto createCard(final TrelloCardDto trelloCardDto) {

        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        if(trelloCard.getName().contains("test")) {
            LOGGER.info("Someone testing application.");
        } else {
            LOGGER.info("Seems that application is used in proper way.");
        }
        return trelloService.createTrelloCard(trelloMapper.mapToCardDto(trelloCard));
    }

}
