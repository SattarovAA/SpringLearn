package com.reactive.tasktracker.service.impl;

import com.reactive.tasktracker.model.Task;
import com.reactive.tasktracker.model.User;
import com.reactive.tasktracker.repository.TaskRepository;
import com.reactive.tasktracker.service.TaskService;
import com.reactive.tasktracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public Flux<Task> findAll() {
        return enrichFlux(taskRepository.findAll());
    }

    public Mono<Task> findById(String id) {
        return enrichMono(taskRepository.findById(id));
    }

    public Mono<Task> save(Task model) {
        model.setCreatedAt(Instant.now());
        model.setUpdatedAt(Instant.now());
        return enrichMono(Mono.just(model)).flatMap(taskRepository::save);
    }

    @Override
    public Mono<Task> addObserver(String taskId, String observerId) {
        return findById(taskId).flatMap(task -> {
            task.getObserverIds().add(observerId);
            return update(taskId, task);
        });
    }

    public Mono<Task> update(String id, Task model) {
        return enrichMono(taskRepository.findById(id).flatMap(modelToUpdate -> taskRepository.save(
                new Task(
                        id,
                        StringUtils.hasText(model.getName()) ? model.getName() : modelToUpdate.getName(),
                        StringUtils.hasText(model.getDescription()) ? model.getDescription() : modelToUpdate.getDescription(),
                        model.getStatus() != null ? model.getStatus() : modelToUpdate.getStatus(),
                        modelToUpdate.getCreatedAt(),
                        Instant.now(),
                        StringUtils.hasText(model.getAuthorId()) ? model.getAuthorId() : modelToUpdate.getAuthorId(),
                        StringUtils.hasText(model.getAssigneeId()) ? model.getAssigneeId() : modelToUpdate.getAssigneeId(),
                        model.getObserverIds() != null ? model.getObserverIds() : modelToUpdate.getObserverIds(),
                        model.getAuthor() != null ? model.getAuthor() : modelToUpdate.getAuthor(),
                        model.getAssignee() != null ? model.getAssignee() : modelToUpdate.getAssignee(),
                        model.getObservers() != null ? model.getObservers() : modelToUpdate.getObservers()
                ))));
    }

    public Mono<Void> deleteById(String id) {
        return taskRepository.deleteById(id);
    }

    public Mono<Void> deleteAll() {
        return taskRepository.deleteAll();
    }

    public Flux<Task> enrichFlux(Flux<Task> taskFlux) {
        Flux<User> author = taskFlux.flatMap(t -> userService.findById(t.getAuthorId())
                .defaultIfEmpty(new User()));
        Flux<User> assignee = taskFlux.flatMap(t -> userService.findById(t.getAssigneeId())
                .defaultIfEmpty(new User()));
        Flux<List<User>> observers = taskFlux.flatMap(task ->
                userService.findAllById(task.getObserverIds())
                        .collectList());
        return Flux.zip(taskFlux, author, assignee, observers)
                .map(t -> new Task(
                                t.getT1().getId(), t.getT1().getName(),
                                t.getT1().getDescription(), t.getT1().getStatus(),
                                t.getT1().getCreatedAt(), t.getT1().getUpdatedAt(),
                                t.getT1().getAuthorId(), t.getT1().getAssigneeId(),
                                t.getT1().getObserverIds(),
                                t.getT2(), t.getT3(), new HashSet<>(t.getT4())
                        )
                );
    }

    public Mono<Task> enrichMono(Mono<Task> taskMono) {
        return enrichFlux(taskMono.flux()).next();
    }
}
