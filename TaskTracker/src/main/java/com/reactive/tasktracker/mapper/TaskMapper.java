package com.reactive.tasktracker.mapper;

import com.reactive.tasktracker.model.Task;
import com.reactive.tasktracker.web.model.request.TaskRequest;
import com.reactive.tasktracker.web.model.response.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    Task requestToModel(TaskRequest taskRequest);

    Task requestToModel(String taskId, TaskRequest taskRequest);

    TaskResponse modelToResponse(Task task);

}

