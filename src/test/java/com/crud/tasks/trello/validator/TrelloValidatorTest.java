package com.crud.tasks.trello.validator;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TrelloValidatorTest {

    @Autowired
    TrelloValidator trelloValidator;

    final Appender mockAppender = mock(Appender.class);

    @Before
        public void init() {
            ch.qos.logback.classic.Logger root =
                    (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
            when(mockAppender.getName()).thenReturn("MOCK");
            root.addAppender(mockAppender);
    }

    @Test
    public void shouldInformAboutTestWhenGetTestedTrelloCard() {
        //Given
        TrelloCard trelloCard = new TrelloCard("test", "test description", "1", "1");
        //When
        boolean actual = trelloValidator.validateCard(trelloCard);
        //Then
        verify(mockAppender).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent) argument).getFormattedMessage().contains("Someone testing application.");
            }
        }));
        Assert.assertFalse(actual);
    }

    @Test
    public void shouldInformIsAppIsUsedProperWhenGetNoTestTrelloCard() {
        //Given
        TrelloCard trelloCard = new TrelloCard("card", "card description", "1", "1");
        //When
        boolean actual = trelloValidator.validateCard(trelloCard);
        //Then
        verify(mockAppender).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent)argument).getFormattedMessage()
                            .contains("Seems that application is used in proper way.");
            }
        }));
        Assert.assertTrue(actual);
    }

    @Test
    public void shouldReturnEmptyListWhenGetTestListOfTrelloBoards() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "test list", false));
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1", "test", trelloLists));
        //When
        List<TrelloBoard> actual = trelloValidator.validateTrelloBorads(trelloBoards);
        //Then
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void shouldReturnListWhenGetProperListOfTrelloBoards() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "list", false));
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1", "list1", trelloLists));
        //When
        List<TrelloBoard> actual = trelloValidator.validateTrelloBorads(trelloBoards);
        //Then
        assertThat(actual).extracting("id").contains(trelloBoards.get(0).getId());
        assertThat(actual).extracting("name").contains(trelloBoards.get(0).getName());
        assertThat(actual).extracting("lists").contains(trelloLists);
    }

}