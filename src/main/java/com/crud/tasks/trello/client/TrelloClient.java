package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.api.accesLink}")
    private String trelloAccesLink;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloAppToken;


    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(buildUrl(), TrelloBoardDto[].class);
        String worldToFind = "Kodilla";

        return Optional.ofNullable(boardsResponse)
                .map(Arrays::asList)
                .map(b-> findBoardContainsString(b,worldToFind))
                .orElse(null);
    }

    private URI buildUrl(){

       return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + trelloAccesLink)
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloAppToken)
                .queryParam("fields", "name,id")
                .build()
                .encode()
                .toUri();
    }

    private List<TrelloBoardDto> findBoardContainsString(List<TrelloBoardDto> listToSearched, String wordToFind){

        return listToSearched
                .stream()
                .filter(z -> z.getId() != null)
                .filter(y -> y.getName() != null)
                .filter(x -> x.getName().contains(wordToFind))
                .collect(Collectors.toList());
    }

}
