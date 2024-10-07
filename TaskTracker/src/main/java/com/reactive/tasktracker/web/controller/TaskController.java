package com.reactive.tasktracker.web.controller;

import com.reactive.tasktracker.mapper.TaskMapper;
import com.reactive.tasktracker.service.TaskService;
import com.reactive.tasktracker.web.model.task.TaskRequest;
import com.reactive.tasktracker.web.model.task.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<TaskResponse> getAll() {
        return taskService.findAll().map(taskMapper::modelToResponse);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<TaskResponse>> getTaskById(@PathVariable("id") String id) {
        return taskService.findById(id)
                .map(taskMapper::modelToResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TaskResponse> create(@RequestBody TaskRequest taskRequest) {
        return taskService.save(taskMapper.requestToModel(taskRequest))
                .map(taskMapper::modelToResponse);
    }


    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/observers")
    public Mono<ResponseEntity<TaskResponse>> addObserver(@RequestParam("taskId") String taskId,
                                                          @RequestParam("observerId") String observerId) {
        return taskService.addObserver(taskId, observerId)
                .map(taskMapper::modelToResponse)
                .map(ResponseEntity::ok);
    }


    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping(path = "/{id}")
    public Mono<ResponseEntity<TaskResponse>> update(@PathVariable("id") String id,
                                                     @RequestBody TaskRequest taskRequest) {
        return taskService.update(id, taskMapper.requestToModel(id, taskRequest))
                .map(taskMapper::modelToResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping(path = "/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") String id) {
        return taskService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping(path = "")
    public Mono<ResponseEntity<Void>> deleteAll() {
        return taskService.deleteAll()
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
