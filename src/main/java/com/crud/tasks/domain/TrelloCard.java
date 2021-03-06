package com.crud.tasks.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TrelloCard {

    private String name;
    private String description;
    private String pos;
    private String listId;

}
