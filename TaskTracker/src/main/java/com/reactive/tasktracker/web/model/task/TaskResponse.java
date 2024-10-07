package com.reactive.tasktracker.web.model.task;

import com.reactive.tasktracker.model.TaskStatus;
import com.reactive.tasktracker.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskResponse {
    private String id;
    private String name;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private TaskStatus status;

    private String authorId;
    private String assigneeId;
    private Set<String> observerIds;

    private User author;
    private User assignee;
    private Set<User> observers;
}
