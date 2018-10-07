package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskMapperTest {

    @Autowired
    TaskMapper taskMapper;

    @Test
    public void testMapToTask() {
        //Given
        TaskDto expected = new TaskDto(1L,"Test", "Test content");
        //When
        Task actual = taskMapper.mapToTask(expected);
        //Then
        Assert.assertEquals(expected.hashCode(), actual.hashCode());
    }

    @Test
    public void mapToTaskDto() {
        //Given
        Task expected = new Task(1L,"Test", "Test content");
        //When
        TaskDto actual = taskMapper.mapToTaskDto(expected);
        //Then
        Assert.assertEquals(expected.hashCode(), actual.hashCode());
    }

    @Test
    public void mapToTaskDtoList() {
        //Given
        List<Task> expected = new ArrayList<>();
        expected.add(new Task(1L,"Test", "Test content"));
        expected.add(new Task(2L,"Test2", "Test content2"));
        expected.add(new Task(3L,"Test3", "Test content3"));
        List<TaskDto> actual = taskMapper.mapToTaskDtoList(expected);
        //When
        List<Long> expectedIds = expected.stream().map(Task::getId).collect(toList());
        List<String> expectedTitles = expected.stream().map(Task::getTitle).collect(toList());
        List<String> expectedContent = expected.stream().map(Task::getContent).collect(toList());
        List<Long> actualIds = actual.stream().map(TaskDto::getId).collect(toList());
        List<String> actualTitles = actual.stream().map(TaskDto::getTitle).collect(toList());
        List<String> actualContent = actual.stream().map(TaskDto::getContent).collect(toList());
        //Then
        Assert.assertTrue(expectedIds.containsAll(actualIds));
        Assert.assertTrue(expectedTitles.containsAll(actualTitles));
        Assert.assertTrue(expectedContent.containsAll(actualContent));
    }

}