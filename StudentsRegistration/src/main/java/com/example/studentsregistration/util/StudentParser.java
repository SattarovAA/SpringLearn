package com.example.studentsregistration.util;

import com.example.studentsregistration.entity.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class StudentParser {

    public static final String ADD_COMMAND_EXAMPLE = "Correct format:\n" +
            "Ivan;Ivanov;19";

    public static Student parseLine(String line) {
        final int INDEX_FIRST_NAME = 0;
        final int INDEX_LAST_NAME = 1;
        final int INDEX_AGE = 2;
        String[] values = line.split(";");

        isCorrectLengthAddCommand(values.length);
        isNumeric(values[INDEX_AGE]);

        return new Student(
                null,
                values[INDEX_FIRST_NAME],
                values[INDEX_LAST_NAME],
                Integer.parseInt(values[INDEX_AGE]));
    }

    public static void isCorrectLengthAddCommand(int length) {
        if (length != 3) {
            throw new IllegalArgumentException("Wrong format. Found " +
                    length + " argument.\n"
                    + ADD_COMMAND_EXAMPLE);
        }
    }

    public static void isNumeric(String numericString) {
        if (!numericString.matches("\\d+")) {
            throw new IllegalArgumentException("Wrong student age format. " + ADD_COMMAND_EXAMPLE);
        }
    }

    public static Collection<Student> readFile(String path) throws IOException {
        BufferedReader inputStream = new BufferedReader(new FileReader(path));
        Collection<Student> students = new ArrayList<>();
        String line = inputStream.readLine();
        while (line != null) {
            try {
                Student student = parseLine(line);
                students.add(student);
                line = inputStream.readLine();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                return students;
            }
        }
        return students;
    }
}
