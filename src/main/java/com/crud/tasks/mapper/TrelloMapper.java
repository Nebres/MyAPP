package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class TrelloMapper {


    public List<TrelloList> mapToList(final List<TrelloListDto> trelloListDto) {
        return Optional
                .ofNullable(trelloListDto)
                .map(tld -> tld
                        .stream()
                        .map(trelloList ->
                                new TrelloList(trelloList.getId(), trelloList.getName(), trelloList.isClosed()))
                        .collect(toList()))
                .orElse(Collections.emptyList());
    }

    public List<TrelloBoard> mapToBoards(final List<TrelloBoardDto> trelloBoardDto) {
        return Optional
                .ofNullable(trelloBoardDto)
                .map(tbd -> tbd
                        .stream()
                        .map(trelloBoard ->
                            new TrelloBoard(
                                    trelloBoard.getId(),
                                    trelloBoard.getName(),
                                    mapToList(trelloBoard.getLists())))
                        .collect(toList()))
                .orElse(Collections.emptyList());
    }

    public List<TrelloListDto> mapToListDto(final List<TrelloList> trelloLists) {
        return Optional
                .ofNullable(trelloLists)
                .map(tl -> tl
                        .stream()
                        .map(trelloList ->
                                new TrelloListDto(trelloList.getId(), trelloList.getName(), trelloList.isClosed()))
                        .collect(toList()))
                .orElse(Collections.emptyList());
    }

    public List<TrelloBoardDto> mapToBoardsDto(final List<TrelloBoard> trelloBoards) {
        return Optional
                .ofNullable(trelloBoards)
                .map(tb -> tb.stream()
                        .map(trelloBoard -> new TrelloBoardDto(trelloBoard.getId(), trelloBoard.getName(),
                                mapToListDto(trelloBoard.getLists())))
                        .collect(toList()))
                .orElse(Collections.emptyList());
    }

    public TrelloCardDto mapToCardDto(final TrelloCard trelloCard) {
        return Optional
                .ofNullable(trelloCard)
                .map(tc -> new TrelloCardDto(tc.getName(), tc.getDescription(), tc.getPos(), tc.getListId()))
                .orElse(new TrelloCardDto());
    }

    public TrelloCard mapToCard(final TrelloCardDto trelloCardDto) {
        return Optional
                .ofNullable(trelloCardDto)
                .map(tcd -> new TrelloCard(tcd.getName(), tcd.getDescription(), tcd.getPos(), tcd.getListId()))
                .orElse(new TrelloCard());
    }

}
