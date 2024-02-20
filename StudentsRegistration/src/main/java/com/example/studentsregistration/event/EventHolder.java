package com.example.studentsregistration.event;

import com.example.studentsregistration.entity.Event;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventHolder extends ApplicationEvent {
    private final Event event;

    public EventHolder(Object source, Event event) {
        super(source);
        this.event = event;
    }
}
