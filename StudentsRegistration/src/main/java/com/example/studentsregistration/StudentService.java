package com.example.studentsregistration;

import com.example.studentsregistration.entity.Event;
import com.example.studentsregistration.entity.Student;
import com.example.studentsregistration.entity.eventType;
import com.example.studentsregistration.event.EventQueue;
import com.example.studentsregistration.util.StudentParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class StudentService {
    private final StudentStorage storage;
    private final EventQueue eventQueue;

    public Collection<Student> findAll() {
        return storage.findAll();
    }

    public void addStudentByLine(String line) {
        Student student = storage.save(StudentParser.parseLine(line.trim()));
        eventQueue.enqueue(new Event(student.id(), eventType.ADD));
    }

    public void addStudentByValues(String firstName, String lastName, int age) {
        Student student = storage.save(new Student(null, firstName, lastName, age));
        eventQueue.enqueue(new Event(student.id(), eventType.ADD));
    }

    public void deleteStudent(int id) {
        Optional<Student> student = storage.findOne(id);
        if (student.isEmpty()) {
            eventQueue.enqueue(new Event(id, eventType.NOT_FOUND));
            return;
        }
        storage.deleteById(id);
        eventQueue.enqueue(new Event(id, eventType.DELETE));
    }

    public void clear() {
        storage.clear();
    }
}
