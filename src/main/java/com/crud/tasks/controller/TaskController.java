package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/task")
public class TaskController {

    @Autowired
    private DbService dbService;
    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET ,value = "getTasks")
    List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(dbService.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTask")
    public TaskDto getTask(@RequestParam Long taskId) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(dbService.getTask(taskId).orElseThrow(TaskNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask")
    public boolean deleteTask(@RequestParam Long taskId) {
        try {
            dbService.deleteTask(taskId);
            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateTask")
    public boolean updateTask(@RequestBody TaskDto taskDto) {
        try {
            TaskDto taskToCheck = getTask(taskDto.getId());
            Task taskToModification = taskMapper.mapToTask(taskToCheck);
            taskToModification.setTitle(taskDto.getTitle());
            taskToModification.setContent(taskDto.getContent());
            taskMapper.mapToTaskDto(dbService.saveTask(taskMapper.mapToTask(taskDto)));
            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTask")
    public void createTask(@RequestBody TaskDto taskDto){
        dbService.saveTask(taskMapper.mapToTask(taskDto));
    }

}
