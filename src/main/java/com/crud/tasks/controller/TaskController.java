package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
            Optional.ofNullable(getTask(taskDto.getId()))
                    .map
            return true;

         /*
            if ( maybeTask.isPresent()) { //użyć ifPresent() , albo mapowanie (wprowadzić metode update() ) | zmienić na boolena
                Task task = maybeTask.get();
                task.setContent(taskDto.getContent());
                task.setTitle(taskDto.getTitle());
                dbService.saveTask(task);
                return true;
            } else {
                return false;
            }
            */
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "createTask")
    public void createTask(@RequestBody TaskDto taskDto){
        dbService.saveTask(taskMapper.mapToTask(taskDto));
    }

    public void changeTask() {
        try {
            Task task = maybeTask.get();
            task.setContent(taskDto.getContent());
            task.setTitle(taskDto.getTitle());
            dbService.saveTask(task);


            task.setContent(task.getContent());
            task.setTitle(task.getTitle());
            dbService.saveTask(task);
        } catch (Exception e) {
            System.err.println(e);
        }
    }



}
