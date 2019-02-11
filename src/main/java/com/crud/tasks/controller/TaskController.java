package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/")
public class TaskController {

    @Autowired
    private DbService dbService;
    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/tasks")
    List<TaskDto> getTasks() {
        return Optional.ofNullable(taskMapper.mapToTaskDtoList(dbService.getAllTasks())).orElse(Collections.emptyList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) {
        return Optional.ofNullable(taskMapper.mapToTaskDto(dbService.getTask(taskId))).orElse(new TaskDto());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/tasks/{taskId}")
    public boolean deleteTask(@PathVariable Long taskId) {
        try {
            dbService.deleteTask(taskId);
            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/tasks")
    public boolean updateTask(@RequestBody TaskDto taskDto) {
        try {
            Optional.ofNullable(getTask(taskDto.getId()))
                    .map(s -> taskMapper.mapToTask(s))
                    .ifPresent(t -> changeTask(t, taskDto));
            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/tasks")
    public boolean createTask(@RequestBody TaskDto taskDto) {
        try {
            dbService.saveTask(taskMapper.mapToTask(taskDto));
            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    private void changeTask(Task task, TaskDto taskDto) {
        task.setTitle(taskDto.getTitle());
        task.setContent(taskDto.getContent());
        dbService.saveTask(task);
    }

}