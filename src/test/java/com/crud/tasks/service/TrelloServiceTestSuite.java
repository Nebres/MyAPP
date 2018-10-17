package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {

    @InjectMocks
    TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    private List<TrelloListDto> initTrelloListDto() {
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1", "test1", false));
        return trelloListDto;
    }

    @Test
    public void shouldFetchTrelloBoardsFromDb() {
        //Given
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(new TrelloBoardDto("1", "test", initTrelloListDto()));
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtoList);
        //When
        List<TrelloBoardDto> actual = trelloService.fetchTrelloBoards();
        List<TrelloBoardDto> expected = trelloBoardDtoList;
        //Then
        assertThat(expected.size()).isEqualTo(actual.size());
        assertThat(expected).extracting("id")
                .contains(actual.get(0).getId());
        assertThat(expected).extracting("name")
                .contains(actual.get(0).getName());
        assertThat(expected).extracting("lists")
                .contains(actual.get(0).getLists());
    }

    @Test
    public void shouldReturnEmptyTrelloBoardsFromDbWhenGetNull() {
        //Given
        when(trelloClient.getTrelloBoards()).thenReturn(null);
        //When
        List<TrelloBoardDto> actual = trelloService.fetchTrelloBoards();
        List<TrelloBoardDto> expected = new ArrayList<>();
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnCreatedTrelloCardInDbWhenGetTrelloCardDto() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto
                ("test", "test description", "top", "1" );
        CreatedTrelloCardDto newCard = new CreatedTrelloCardDto
                ("1", "test", "test.com");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(newCard);
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");
        doNothing().when(emailService).send(any());
        //When
        CreatedTrelloCardDto actual = trelloService.createTrelloCard(trelloCardDto);
        CreatedTrelloCardDto expected = newCard;
        //Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldReturnNullWhenGetNull() {
        //Given
        TrelloCardDto trelloCardDto = null;
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(null);
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");
        doNothing().when(emailService).send(any());
        //When
        CreatedTrelloCardDto actual = trelloService.createTrelloCard(trelloCardDto);
        //Then
        Assert.assertNull(actual);
    }

}