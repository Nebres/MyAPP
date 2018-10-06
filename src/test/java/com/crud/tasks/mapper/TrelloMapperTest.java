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

import static java.util.stream.Collectors.toList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTest {

    @Autowired
    TrelloMapper trelloMapper;

    private List<TrelloList> initTrelloList() {
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("1", "test1", false));
        trelloList.add(new TrelloList("2", "test2", false));
        trelloList.add(new TrelloList("3", "test3", true));
        return trelloList;
    }

    private List<TrelloListDto> initTrelloListDto() {
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1", "test1", false));
        trelloListDto.add(new TrelloListDto("2", "test2", false));
        trelloListDto.add(new TrelloListDto("3", "test3", true));
        return trelloListDto;
    }

    private List<TrelloBoard> initTrelloBoards() {
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1", "Test1", initTrelloList()));
        trelloBoards.add(new TrelloBoard("2", "Test2", initTrelloList()));
        trelloBoards.add(new TrelloBoard("3", "Test3", initTrelloList()));
        return trelloBoards;
    }

    private List<TrelloBoardDto> initTrelloBoardsDto() {
            List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
            trelloBoardsDto.add(new TrelloBoardDto("1", "Test1", initTrelloListDto()));
            trelloBoardsDto.add(new TrelloBoardDto("2", "Test2", initTrelloListDto()));
            trelloBoardsDto.add(new TrelloBoardDto("3", "Test3", initTrelloListDto()));
            return trelloBoardsDto;
    }

    @Test
    public void mapToList() {
        //Given
        List<TrelloListDto> expected = initTrelloListDto();
        List<TrelloList> actual = trelloMapper.mapToList(expected);
        //When
        List<String> expectedIds = expected.stream().map(TrelloListDto::getId).collect(toList());
        List<String> expectedNames = expected.stream().map(TrelloListDto::getName).collect(toList());
        List<Boolean> expectedIsClosed = expected.stream().map(TrelloListDto::isClosed).collect(toList());
        List<String> actualIds = actual.stream().map(TrelloList::getId).collect(toList());
        List<String> actualNames = actual.stream().map(TrelloList::getName).collect(toList());
        List<Boolean> actualIsClosed = actual.stream().map(TrelloList::isClosed).collect(toList());
        //Then
        Assert.assertTrue(expectedIds.containsAll(actualIds));
        Assert.assertTrue(expectedNames.containsAll(actualNames));
        Assert.assertTrue(expectedIsClosed.containsAll(actualIsClosed));
    }

    @Test
    public void mapToBoards() {
        //Given
        List<TrelloBoardDto> expected = initTrelloBoardsDto();
        List<TrelloBoard> actual = trelloMapper.mapToBoards(expected);
        //When
        List<String> expectedIds = expected.stream().map(TrelloBoardDto::getId).collect(toList());
        List<String> expectedNames = expected.stream().map(TrelloBoardDto::getName).collect(toList());
        Long expectedSumedListsSize = expected.stream()
                .mapToInt(TrelloBoardDto -> TrelloBoardDto.getLists().size())
                .count();
        List<String> actualIds = actual.stream().map(TrelloBoard::getId).collect(toList());
        List<String> actualNames = actual.stream().map(TrelloBoard::getName).collect(toList());
        Long actualSumedListsSize = actual.stream()
                .mapToInt(TrelloBoardDto -> TrelloBoardDto.getLists().size())
                .count();
        //Then
        Assert.assertTrue(expectedIds.containsAll(actualIds));
        Assert.assertTrue(expectedNames.containsAll(actualNames));
        Assert.assertEquals(expectedSumedListsSize, actualSumedListsSize);
    }

    @Test
    public void mapToListDto() {
        //Given
        List<TrelloList> expected = initTrelloList();
        List<TrelloListDto> actual = trelloMapper.mapToListDto(expected);
        //When
        List<String> expectedIds = expected.stream().map(TrelloList::getId).collect(toList());
        List<String> expectedNames = expected.stream().map(TrelloList::getName).collect(toList());
        List<Boolean> expectedIsClosed = expected.stream().map(TrelloList::isClosed).collect(toList());
        List<String> actualIds = actual.stream().map(TrelloListDto::getId).collect(toList());
        List<String> actualNames = actual.stream().map(TrelloListDto::getName).collect(toList());
        List<Boolean> actualIsClosed = actual.stream().map(TrelloListDto::isClosed).collect(toList());
        //Then
        Assert.assertTrue(expectedIds.containsAll(actualIds));
        Assert.assertTrue(expectedNames.containsAll(actualNames));
        Assert.assertTrue(expectedIsClosed.containsAll(actualIsClosed));
    }

    @Test
    public void mapToBoardsDto() {
        //Given
        List<TrelloBoard> expected = initTrelloBoards();
        List<TrelloBoardDto> actual = trelloMapper.mapToBoardsDto(expected);
        //When
        List<String> expectedIds = expected.stream().map(TrelloBoard::getId).collect(toList());
        List<String> expectedNames = expected.stream().map(TrelloBoard::getName).collect(toList());
        Long expectedSumedListsSize = expected.stream()
                .mapToInt(TrelloBoardDto -> TrelloBoardDto.getLists().size())
                .count();
        List<String> actualIds = actual.stream().map(TrelloBoardDto::getId).collect(toList());
        List<String> actualNames = actual.stream().map(TrelloBoardDto::getName).collect(toList());
        Long actualSumedListsSize = actual.stream()
                .mapToInt(TrelloBoardDto -> TrelloBoardDto.getLists().size())
                .count();
        //Then
        Assert.assertTrue(expectedIds.containsAll(actualIds));
        Assert.assertTrue(expectedNames.containsAll(actualNames));
        Assert.assertEquals(expectedSumedListsSize, actualSumedListsSize);
    }

    @Test
    public void mapToCardDto() {
        //Given
        TrelloCard expected = new TrelloCard("test","test description","1","1" );
        //When
        TrelloCardDto actual  = trelloMapper.mapToCardDto(expected);
        //Then
        Assert.assertEquals(actual.hashCode(), expected.hashCode());
    }

    @Test
    public void mapToCard() {
        //Given
        TrelloCardDto expected = new TrelloCardDto("test","test description","1","1" );
        //When
        TrelloCard actual  = trelloMapper.mapToCard(expected);
        //Then
        Assert.assertEquals(actual.hashCode(), expected.hashCode());
    }

}