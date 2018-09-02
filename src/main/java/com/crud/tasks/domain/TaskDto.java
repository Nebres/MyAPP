package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class TaskDto {

    private Long id;
    private String title;
    private String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskDto)) return false;
        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(getId(), taskDto.getId()) &&
                Objects.equals(getTitle(), taskDto.getTitle()) &&
                Objects.equals(getContent(), taskDto.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getContent());
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}
