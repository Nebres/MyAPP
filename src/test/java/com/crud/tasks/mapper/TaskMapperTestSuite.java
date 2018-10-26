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
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskMapperTestSuite {

    @Autowired
    TaskMapper taskMapper;

    @Test
    public void shouldReturnTaskWhenGetTaskDto() {
        //Given
        TaskDto taskDto = new TaskDto(1L,"Test", "Test content");
        //When
        Task expected = new Task(1L,"Test", "Test content");
        Task actual = taskMapper.mapToTask(taskDto);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyTaskWhenGetNull() {
        //Given
        TaskDto taskDto = null;
        //When
        Task expected = new Task();
        Task actual = taskMapper.mapToTask(taskDto);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyTaskWhenGetEmptyTaskDto() {
        //Given
        TaskDto taskDto = new TaskDto();
        //When
        Task expected = new Task();
        Task actual = taskMapper.mapToTask(taskDto);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnTaskDtoWhenGetTask() {
        //Given
        Task task = new Task(1L,"Test", "Test content");
        //When
        TaskDto expected = new TaskDto(1L,"Test", "Test content");
        TaskDto actual = taskMapper.mapToTaskDto(task);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyTaskDtoWhenGetNull() {
        //Given
        Task task = null;
        //When
        TaskDto expected = new TaskDto();
        TaskDto actual = taskMapper.mapToTaskDto(task);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyTaskDtoWhenGetEmptyTask() {
        //Given
        Task task = new Task();
        //When
        TaskDto expected = new TaskDto();
        TaskDto actual = taskMapper.mapToTaskDto(task);
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnListOfTasksDtoWhenGetListOfTasks() {
        //Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L,"Test", "Test content"));
        tasks.add(new Task(2L,"Test2", "Test content2"));
        tasks.add(new Task(3L,"Test3", "Test content3"));
        //When
        List<TaskDto> expected = new ArrayList<>();
        expected.add(new TaskDto(1L,"Test", "Test content"));
        expected.add(new TaskDto(2L,"Test2", "Test content2"));
        expected.add(new TaskDto(3L,"Test3", "Test content3"));
        List<TaskDto> actual = taskMapper.mapToTaskDtoList(tasks);
        //Then
        assertThat(expected.size()).isEqualTo(actual.size());
        assertThat(expected).extracting("id")
                .contains(actual.get(0).getId(), actual.get(1).getId(), actual.get(2).getId());
        assertThat(expected).extracting("title")
                .contains(actual.get(0).getTitle(), actual.get(1).getTitle(), actual.get(2).getTitle());
        assertThat(expected).extracting("content")
                .contains(actual.get(0).getContent(), actual.get(1).getContent(), actual.get(2).getContent());
    }

    @Test
    public void shouldReturnEmptyListOfTasksDtoWhenGetEmptyListOfTasks() {
        //Given
        List<Task> tasks = new ArrayList<>();
        //When
        List<TaskDto> actual = taskMapper.mapToTaskDtoList(tasks);
        //Then
        Assert.assertNotNull(actual);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void shouldReturnEmptyTaskDtoListWhenGetNull() {
        //Given
        List<Task> tasks = null;
        //When
        List<TaskDto> actual = taskMapper.mapToTaskDtoList(tasks);
        //Then
        Assert.assertNotNull(actual);
        Assert.assertTrue(actual.isEmpty());
    }

}