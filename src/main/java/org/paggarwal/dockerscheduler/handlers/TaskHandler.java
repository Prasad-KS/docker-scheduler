package org.paggarwal.dockerscheduler.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;
import org.paggarwal.dockerscheduler.Answer;
import org.paggarwal.dockerscheduler.RequestHandlerWrapper;
import org.paggarwal.dockerscheduler.models.Task;
import org.paggarwal.dockerscheduler.models.TaskExecutionPayload;
import org.paggarwal.dockerscheduler.service.TaskService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.lang.reflect.Type;

/**
 * Created by paggarwal on 3/7/16.
 */
@Service
public class TaskHandler {
    @Inject
    private TaskService taskService;

    public RequestHandlerWrapper<EmptyPayload> listTasks() {
        return new RequestHandlerWrapper<EmptyPayload>(EmptyPayload.TYPE_REFERENCE,(value, urlParams, headers) -> {
            return new Answer(200,taskService.listTasks());
        });
    }

    public RequestHandlerWrapper<EmptyPayload> listScheduledTasks() {
        return new RequestHandlerWrapper<EmptyPayload>(EmptyPayload.TYPE_REFERENCE,(value, urlParams, headers) -> {
            return new Answer(200,taskService.listScheduledTasks());
        });
    }

    public RequestHandlerWrapper<EmptyPayload> getTask() {
        return new RequestHandlerWrapper<EmptyPayload>(EmptyPayload.TYPE_REFERENCE,(value, urlParams, headers) -> {
            return new Answer(200,taskService.get(Integer.parseInt(urlParams.get(":id"))).orElse(null));
        });
    }

    public RequestHandlerWrapper<Task> create() {
        return new RequestHandlerWrapper<Task>(Task.TYPE_REFERENCE,(value, urlParams, headers) -> {
            return new Answer(201,ImmutableMap.of("id",taskService.create(value)));
        });
    }

    public RequestHandlerWrapper<EmptyPayload> delete() {
        return new RequestHandlerWrapper<EmptyPayload>(EmptyPayload.TYPE_REFERENCE,(value, urlParams, headers) -> {
            return new Answer(200,ImmutableMap.of("status", taskService.delete((int) Integer.parseInt(urlParams.get(":id")))));
        });
    }

    public RequestHandlerWrapper<TaskExecutionPayload> executeTask() {
        return new RequestHandlerWrapper<TaskExecutionPayload>(TaskExecutionPayload.TYPE, (value, urlParams, headers) -> {
            return new Answer(200, ImmutableMap.of("status", taskService.execute(urlParams.get(":name"), value.getPayload())));
        });
    }
}
