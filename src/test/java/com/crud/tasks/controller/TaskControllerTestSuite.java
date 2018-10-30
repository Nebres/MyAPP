package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DbService dbService;

    @MockBean
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
        return tasks;
    }

    private List<TaskDto> initTasksDtoList() {
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(0, initTaskDto(1L));
        tasks.add(1, initTaskDto(2L));
        return tasks;
    }

    @Test
    public void shouldReturnAllTasksFromTheDb() throws Exception {
        //Given
        List<Task> tasks = initTasksList();
        List<TaskDto> tasksDto = initTasksDtoList();
        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(tasksDto);
        //When & Then
        mockMvc.perform(get("/v1/tasks/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test1")))
                .andExpect(jsonPath("$[0].content", is("test content1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("test2")))
                .andExpect(jsonPath("$[1].content", is("test content2")));
    }

    @Test
    public void shouldReturnSpecifiedExistedTaskFromTheDbWhenCorrectIdIsGiven() throws Exception {
        //Given
        List<Task> tasks = initTasksList();
        Task task = initTask(1L);
        TaskDto taskDto = initTaskDto(1L);
        when(dbService.getTask(1L)).thenReturn(tasks.get(0));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        //When & Then
        mockMvc.perform(get("/v1/tasks/1/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test1")))
                .andExpect(jsonPath("$.content", is("test content1")));
    }


    @Test
    public void shouldReturnEmptyTaskFromTheDbWhenNonExistedIdIsGiven() throws Exception {
        //Given
        List<Task> tasks = initTasksList();
        tasks.add(2, new Task());
        when(dbService.getTask(3L)).thenReturn(tasks.get(2));
        when(taskMapper.mapToTaskDto(tasks.get(2))).thenReturn(new TaskDto());
        //When & Then
        mockMvc.perform(get("/v1/tasks/3/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", isEmptyOrNullString()))
                .andExpect(jsonPath("$.title", isEmptyOrNullString()))
                .andExpect(jsonPath("$.content", isEmptyOrNullString()));
    }


    @Test
    public void shouldDeleteSpecifiedTaskFromTheDbWhenCorrectIdIsGiven() throws Exception {
        //Given
        List<Task> tasks = initTasksList();
        doNothing().when(dbService).deleteTask(anyLong());
        //When & Then
        mockMvc.perform(delete("/v1/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    public void shouldPostTaskInToTheDb() throws Exception {
        //Given
        Gson gSon = new Gson();
        Task createdTask = initTask(1L);
        TaskDto createdTaskDto = initTaskDto(1L);
        when(dbService.saveTask(createdTask)).thenReturn(createdTask);
        when(taskMapper.mapToTaskDto(createdTask)).thenReturn(createdTaskDto);
        String jSonContent = gSon.toJson(createdTask);
        //When & Then
        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jSonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    public void shouldUpdateTaskInTheDb() throws Exception {
        Gson gSon = new Gson();
        Task task = initTask(1L);
        Task updatedTask = new Task(1L, "test", "test content after change");
        TaskDto updatedTaskDto = new TaskDto(1L, "test", "test content after change");
        when(dbService.saveTask(task)).thenReturn(updatedTask);
        when(taskMapper.mapToTaskDto(updatedTask)).thenReturn(updatedTaskDto);
        String jSonContent = gSon.toJson(updatedTask);
        //When & Then
        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jSonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    public void shouldReturnEmptyListWhenNoTasksIsInDb() throws Exception {
        //Given
        List<Task> tasks = Collections.emptyList();
        List<TaskDto> tasksDto = Collections.emptyList();
        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(tasksDto);
        //When & Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(Collections.EMPTY_LIST)));
    }

}