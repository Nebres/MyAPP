package com.crud.tasks.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Task {

    private Long id;
    private String title;
    private String content;

}
