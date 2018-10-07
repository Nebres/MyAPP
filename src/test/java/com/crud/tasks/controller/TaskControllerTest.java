package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest {

    @InjectMocks
    TaskController taskController;

    @Mock
    DbService dbService;

    @Mock
    TaskMapper taskMapper;

    private Task initTask(Long number) {
        return new Task(number, "test" + number, "test content" + number);
    }

    private TaskDto initTaskDto(Long number) {
        return new TaskDto(number, "test" + number, "test content" + number);
    }

    private List<Task> initTasksList() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(0, initTask(1L));
        tasks.add(1, initTask(2L));
        tasks.add(2, initTask(3L));
        return tasks;
    }

    private List<TaskDto> initTasksDtoList() {
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(0, initTaskDto(1L));
        tasks.add(1, initTaskDto(2L));
        tasks.add(2, initTaskDto(3L));
        return tasks;
    }

    @Test
    public void testGetTasks() {
        //Given
        when(dbService.getAllTasks()).thenReturn(initTasksList());
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(initTasksDtoList());
        List<Task> actual = initTasksList();
        List<TaskDto> expected = taskController.getTasks();
        //When
        List<Long> expectedIds = expected.stream().map(TaskDto::getId).collect(toList());
        List<String> expectedTitles = expected.stream().map(TaskDto::getTitle).collect(toList());
        List<String> expectedContent = expected.stream().map(TaskDto::getContent).collect(toList());
        List<Long> actualIds = actual.stream().map(Task::getId).collect(toList());
        List<String> actualTitles = actual.stream().map(Task::getTitle).collect(toList());
        List<String> actualContent = actual.stream().map(Task::getContent).collect(toList());
        //Then
        Assert.assertTrue(expectedIds.containsAll(actualIds));
        Assert.assertTrue(expectedTitles.containsAll(actualTitles));
        Assert.assertTrue(expectedContent.containsAll(actualContent));
    }

    @Test
    public void testGetTask() {
        //Given
        List<Task> tasks = initTasksList();
        when(dbService.getTask(1L)).thenReturn(tasks.get(0));
        when(taskMapper.mapToTaskDto(anyObject())).thenReturn(initTaskDto(1L));
        TaskDto expected = initTaskDto(1L);
        //When
        TaskDto actual = taskController.getTask(1L);
        //Then
        Assert.assertEquals(expected, actual);
    }

}