package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private TrelloMapper trelloMapper;


    @Test
    public void shouldFetchEmptyList() {
        //Given
        List<TrelloListDto> trelloDtoLists = new ArrayList<>();
        trelloDtoLists.add(new TrelloListDto("1", "test list", false));
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "test", trelloDtoLists));
        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "test list", false));
        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(new ArrayList());
        when(trelloValidator.validateTrelloBorads(mappedTrelloBoards)).thenReturn(new ArrayList<>());
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();
        //Then
        Assert.assertNotNull(trelloBoardDtos);
        Assert.assertEquals(0, trelloBoardDtos.size());

    }

    @Test
    public void shouldFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloDtoLists = new ArrayList<>();
        trelloDtoLists.add(new TrelloListDto("1", "My list", false));
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "My task", trelloDtoLists));
        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "My list", false));
        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1", "My task", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);
        when(trelloValidator.validateTrelloBorads(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();
        //Then
        Assert.assertNotNull(trelloBoardDtos);
        Assert.assertEquals(1, trelloBoardDtos.size());
        trelloBoardDtos.forEach(trelloBoardDto -> {
            Assert.assertEquals("1", trelloBoardDto.getId());
            Assert.assertEquals("My task", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                Assert.assertEquals("1",  trelloListDto.getId());
                Assert.assertEquals("My list", trelloListDto.getName());
                Assert.assertEquals(false, trelloListDto.isClosed());
            });
        });
    }

    @Test
    public void shouldCreateTrelloCard(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("card", "card des", "1", "1");
        TrelloCard trelloCard = new TrelloCard("card", "card", "1", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "card", "card.com");

        when(trelloService.createTrelloCard(any())).thenReturn(createdTrelloCardDto);
        when(trelloMapper.mapToCard(any())).thenReturn(trelloCard);
        when(trelloValidator.validateCard(any())).thenReturn(true);
        //When
        CreatedTrelloCardDto actual = trelloFacade.createCard(trelloCardDto);
        CreatedTrelloCardDto expected =  new CreatedTrelloCardDto("1", "card", "card.com");
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldCreateEmptyTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto();
        TrelloCard trelloCard = new TrelloCard("card", "card", "1", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto();

        when(trelloService.createTrelloCard(any())).thenReturn(createdTrelloCardDto);
        when(trelloMapper.mapToCard(any())).thenReturn(trelloCard);
        when(trelloValidator.validateCard(any())).thenReturn(true);
        //When
        CreatedTrelloCardDto actual = trelloFacade.createCard(trelloCardDto);
        CreatedTrelloCardDto expected = new CreatedTrelloCardDto();
        //Then
        Assert.assertEquals(expected, actual);
    }

}