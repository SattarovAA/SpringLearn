package com.example.studentsregistration;

import com.example.studentsregistration.entity.Student;
import com.example.studentsregistration.util.StudentParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class StudentStorage {
    private final Map<Integer, Student> students = new HashMap<>();
    private int idCount;

    public StudentStorage() {
    }

    public StudentStorage(String path) {
        try {
            Collection<Student> collection = StudentParser.readFile(path);
            collection.forEach(this::save);
        } catch (IOException e) {
            System.err.println("Reading error initialization file.\n" +
                    "Incorrect path:" + path);
        }
    }

    public Collection<Student> findAll() {
        return students.values();
    }

    public Optional<Student> findOne(int id) {
        return Optional.ofNullable(students.get(id));
    }

    public void deleteById(int id) {
        students.remove(id);
    }

    public void clear() {
        students.clear();
    }

    public Student save(Student student) {
        student = new Student(++idCount, student.firstName(),
                student.lastName(), student.age());
        students.putIfAbsent(idCount, student);
        return student;
    }
}
