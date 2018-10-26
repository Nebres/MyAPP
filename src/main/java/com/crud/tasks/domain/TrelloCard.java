package com.crud.tasks.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class TrelloCard {

    private String name;
    private String description;
    private String pos;
    private String listId;

}
