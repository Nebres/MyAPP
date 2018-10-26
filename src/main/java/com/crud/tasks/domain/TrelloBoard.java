package com.crud.tasks.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class TrelloBoard {

    private String id;
    private String name;
    private List<TrelloList> lists;

}
