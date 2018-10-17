package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class TaskMapper {

    public Task mapToTask(final TaskDto taskDto) {
        return Optional.ofNullable(taskDto).map(td -> new Task(td.getId(), td.getTitle(), td.getContent()))
                .orElse(new Task());
    }

    public TaskDto mapToTaskDto(final Task task) {
        return Optional.ofNullable(task).map(t -> new TaskDto(t.getId(), t.getTitle(), t.getContent()))
                .orElse(new TaskDto());
    }

    public List<TaskDto> mapToTaskDtoList (final List<Task> taskList) {
        return Optional.ofNullable(taskList)
                .map(x -> x.stream()
                        .map(t -> new TaskDto(t.getId(), t.getTitle(), t.getContent()))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

}
