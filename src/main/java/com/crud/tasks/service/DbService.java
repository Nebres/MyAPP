package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class DbService {

    @Autowired
    private TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTask(final Long id) {
        return repository.findById(id);
    }

    public Task saveTask(final Task task) {
        return repository.save(task);
    }

    public void deleteTask(Long id) {
        repository.delete(id);
    }

}