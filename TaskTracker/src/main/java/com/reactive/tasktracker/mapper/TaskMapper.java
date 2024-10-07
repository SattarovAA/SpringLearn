package com.reactive.tasktracker.mapper;

import com.reactive.tasktracker.model.Task;
import com.reactive.tasktracker.web.model.task.TaskRequest;
import com.reactive.tasktracker.web.model.task.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    Task requestToModel(TaskRequest taskRequest);

    Task requestToModel(String taskId, TaskRequest taskRequest);

    TaskResponse modelToResponse(Task task);

}

