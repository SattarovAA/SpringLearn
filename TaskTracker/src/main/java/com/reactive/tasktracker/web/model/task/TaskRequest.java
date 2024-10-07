package com.reactive.tasktracker.web.model.task;

import com.reactive.tasktracker.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskRequest {
    private String id;
    private String name;
    private String description;
    private TaskStatus status;

    private String authorId;
    private String assigneeId;
    private Set<String> observerIds;
}
