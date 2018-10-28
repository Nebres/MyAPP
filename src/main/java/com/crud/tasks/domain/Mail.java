package com.crud.tasks.domain;

import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Mail {

    private final String mailTo;
    private final String mailToCC;
    private final String subject;
    private final String message;
    private boolean isScheduler;



}
