package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.trello.facade.TrelloFacade;
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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
    TaskController taskController;

    private TaskDto initTaskDto(Long number) {
        return new TaskDto(number, "test" + number, "test content" + number);
    }

    private List<TaskDto> initTasksDtoList() {
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(0, initTaskDto(1L));
        tasks.add(1, initTaskDto(2L));
        return tasks;
    }

    @Test
    public void shouldReturnAllTasksFromTheList() throws Exception {
        //Given
        List<TaskDto> tasks = initTasksDtoList();
        when(taskController.getTasks()).thenReturn(tasks);
        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
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
    public void shouldReturnSpecifiedTaskFromTheList() throws Exception {
        //Given
        List<TaskDto> tasks = initTasksDtoList();
        when(taskController.getTask(1L)).thenReturn(tasks.get(0));
        //When & Then
        mockMvc.perform(get("/v1/task/getTask?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test1")))
                .andExpect(jsonPath("$.content", is("test content1")));

    }

    @Test
    public void shouldDeleteSpecifiedTaskFromTheList() throws Exception {
        //Given
        when(taskController.deleteTask(1L)).thenReturn(true);
        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    public void shouldPostTaskInToTheList() throws Exception {
        //Given
        Gson gSon = new Gson();
        TaskDto createdTask = initTaskDto(1L);
        doNothing().when(taskController).createTask(createdTask);
        String jSonContent = gSon.toJson(createdTask);
        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jSonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateTaskInTheList() throws Exception {
        //Given
        Gson gSon = new Gson();
        TaskDto updatedTask = initTaskDto(1L);
        when(taskController.updateTask(updatedTask)).thenReturn(true);
        String jSonContent = gSon.toJson(updatedTask);
        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jSonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));;
    }

}