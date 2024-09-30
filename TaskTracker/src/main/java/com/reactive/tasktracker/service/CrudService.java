package com.reactive.tasktracker.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CrudService<T> {
    Flux<T> findAll();

    Mono<T> findById(String id);

    Mono<T> save(T model);

    Mono<T> update(String id, T model);

    Mono<Void> deleteById(String id);

    Mono<Void> deleteAll();
}
