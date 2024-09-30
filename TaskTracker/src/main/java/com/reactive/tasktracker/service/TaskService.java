package com.reactive.tasktracker.service;

import com.reactive.tasktracker.model.Task;
import reactor.core.publisher.Mono;

public interface TaskService extends CrudService<Task> {
    Mono<Task> addObserver(String taskId, String observerId);
}
