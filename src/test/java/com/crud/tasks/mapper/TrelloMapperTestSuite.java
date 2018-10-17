package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    private List<TrelloList> initTrelloList() {
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("1", "test1", false));
        return trelloList;
    }

    private List<TrelloListDto> initTrelloListDto() {
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1", "test1", false));
        return trelloListDto;
    }

    private List<TrelloBoard> initTrelloBoards() {
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1", "Test1", initTrelloList()));

        return trelloBoards;
    }

    private List<TrelloBoardDto> initTrelloBoardsDto() {
            List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
            trelloBoardsDto.add(new TrelloBoardDto("1", "Test1", initTrelloListDto()));
            return trelloBoardsDto;
    }

    @Test
    public void shouldMapToTrelloListWhenGetTrelloListDto() {
        //Given
        List<TrelloListDto> listDto = initTrelloListDto();
        //When
        List<TrelloList> expected = new ArrayList<>();
        expected.add(new TrelloList("1", "test1", false));
        List<TrelloList> actual = trelloMapper.mapToList(listDto);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyTrelloListWhenGetEmptyTrelloListDto() {
        //Given
        List<TrelloListDto> listDto = new ArrayList<>();
        //When
        List<TrelloList> expected = new ArrayList<>();
        List<TrelloList> actual = trelloMapper.mapToList(listDto);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyTrelloListWhenGetNull() {
        //Given
        List<TrelloListDto> listDto = null;
        //When
        List<TrelloList> expected = new ArrayList<>();
        List<TrelloList> actual = trelloMapper.mapToList(listDto);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldMapToBoardWhenGetBoardDto() {
        //Given
        List<TrelloBoardDto> boardDto = initTrelloBoardsDto();
        //When
        List<TrelloBoard> expected = new ArrayList<>();
        expected.add(new TrelloBoard("1", "Test1", initTrelloList()));
        List<TrelloBoard> actual = trelloMapper.mapToBoards(boardDto);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyBoardWhenGetEmptyBoardDto() {
        //Given
        List<TrelloBoardDto> boardDto = new ArrayList<>();
        //When
        List<TrelloBoard> expected = new ArrayList<>();
        List<TrelloBoard> actual = trelloMapper.mapToBoards(boardDto);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyBoardWhenGetNull() {
        //Given
        List<TrelloBoardDto> boardDto = null;
        //When
        List<TrelloBoard> expected = new ArrayList<>();
        List<TrelloBoard> actual = trelloMapper.mapToBoards(boardDto);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldMapToTrelloListDtoWhenGetTrelloList() {
        //Given
        List<TrelloList> list = initTrelloList();
        //When
        List<TrelloListDto> expected = new ArrayList<>();
        expected.add(new TrelloListDto("1", "test1", false));
        List<TrelloListDto> actual = trelloMapper.mapToListDto(list);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyTrelloListDtoWhenGetEmptyTrelloList() {
        //Given
        List<TrelloList> list = new ArrayList<>();
        //When
        List<TrelloListDto> expected = new ArrayList<>();
        List<TrelloListDto> actual = trelloMapper.mapToListDto(list);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyTrelloListDtoWhenGetNull() {
        //Given
        List<TrelloList> list = null;
        //When
        List<TrelloListDto> expected = new ArrayList<>();
        List<TrelloListDto> actual = trelloMapper.mapToListDto(list);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldMapToBoardDtoWhenGetBoard() {
        //Given
        List<TrelloBoard> board = initTrelloBoards();
        //When
        List<TrelloBoardDto> expected = new ArrayList<>();
        expected.add(new TrelloBoardDto("1", "Test1", initTrelloListDto()));
        List<TrelloBoardDto> actual = trelloMapper.mapToBoardsDto(board);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyBoardDtoWhenGetEmptyBoard() {
        //Given
        List<TrelloBoard> board = new ArrayList<>();
        //When
        List<TrelloBoardDto> expected = new ArrayList<>();
        List<TrelloBoardDto> actual = trelloMapper.mapToBoardsDto(board);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyBoardDtoWhenGetNull() {
        //Given
        List<TrelloBoard> board = null;
        //When
        List<TrelloBoardDto> expected = new ArrayList<>();
        List<TrelloBoardDto> actual = trelloMapper.mapToBoardsDto(board);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldMapToTrelloCardDtoWhenGetTrelloCard() {
        //Given
        TrelloCard trelloCard = new TrelloCard("test", "test description", "1", "1");
        //When
        TrelloCardDto expected = new TrelloCardDto("test", "test description", "1", "1");
        TrelloCardDto actual = trelloMapper.mapToCardDto(trelloCard);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyTrelloCardDtoWhenGetEmptyTrelloCard() {
        //Given
        TrelloCard trelloCard = new TrelloCard();
        //When
        TrelloCardDto expected = new TrelloCardDto();
        TrelloCardDto actual = trelloMapper.mapToCardDto(trelloCard);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyTrelloCardDtoWhenGetNull() {
        //Given
        TrelloCard trelloCard = null;
        //When
        TrelloCardDto expected = new TrelloCardDto();
        TrelloCardDto actual = trelloMapper.mapToCardDto(trelloCard);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldMapToTrelloCardWhenGetTrelloCardDto() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test","test description","1","1" );
        //When
        TrelloCard expected = new TrelloCard("test", "test description", "1", "1");
        TrelloCard actual  = trelloMapper.mapToCard(trelloCardDto);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyTrelloCardWhenGetEmptyTrelloCardDto() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto();
        //When
        TrelloCard expected = new TrelloCard();
        TrelloCard actual  = trelloMapper.mapToCard(trelloCardDto);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyTrelloCardWhenGetNull() {
        //Given
        TrelloCardDto trelloCardDto = null;
        //When
        TrelloCard expected = new TrelloCard();
        TrelloCard actual  = trelloMapper.mapToCard(trelloCardDto);
        //Then
        Assert.assertEquals(expected, actual);
    }

}