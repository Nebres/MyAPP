package com.crud.tasks.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TrelloCardDto {

    private String name;
    private String description;
    private String pos;
    private String listId;

}
