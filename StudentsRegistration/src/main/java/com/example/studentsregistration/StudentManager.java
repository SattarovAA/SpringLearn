package com.example.studentsregistration;

import com.example.studentsregistration.entity.Student;
import com.example.studentsregistration.entity.Event;
import com.example.studentsregistration.event.EventHolder;
import com.example.studentsregistration.event.EventQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@RequiredArgsConstructor
@ShellComponent
public class StudentManager {
    private final StudentService service;
    private final EventQueue eventQueue;
    private final ApplicationEventPublisher publisher;

    @EventListener(ApplicationStartedEvent.class)
    public void startListener() {
        eventWorker();
    }

    private void eventWorker() {
        Thread eventConsumerThread = new Thread(() -> {
            while (true) {
                Event event = eventQueue.dequeue();
                publisher.publishEvent(new EventHolder(this, event));
            }
        });
        eventConsumerThread.start();
    }

    @ShellMethod
    public String print() {
        return service.findAll()
                .stream()
                .map(Student::toString)
                .reduce("Студенты:",
                        (a, b) -> a.concat("\n")
                                .concat(b)
                );
    }

    @ShellMethod(key = "addLine")
    public void addByLine(@ShellOption(value = "st") String studentLine) {
        try {
            service.addStudentByLine(studentLine);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @ShellMethod(key = "a")
    public void addByValues(@ShellOption(value = "fn") String firstName,
                            @ShellOption(value = "ln") String lastName,
                            @ShellOption(value = "age") int age) {
        service.addStudentByValues(firstName, lastName, age);
    }

    @ShellMethod(key = "d")
    public void deleteById(@ShellOption(value = "id") int id) {
        service.deleteStudent(id);
    }

    @ShellMethod(key = "cl")
    public String clearStudentsList() {
        service.clear();
        return "Students database cleared";
    }
}
