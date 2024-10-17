package com.rest.hotelbooking.mapper;

import java.util.List;

public interface SimpleRequestResponseMapper<Entity, Request, Response> {
    Entity requestToModel(Long modelId, Request request);
    Entity requestToModel(Request request);

    Response modelToResponse(Entity model);
    List<Response> modelListToResponseList(List<Entity> modelList);
}
