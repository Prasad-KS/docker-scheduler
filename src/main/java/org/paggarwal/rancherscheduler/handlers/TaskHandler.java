package org.paggarwal.rancherscheduler.handlers;

import com.google.common.collect.ImmutableMap;
import org.paggarwal.rancherscheduler.RequestHandlerWrapper;
import org.paggarwal.rancherscheduler.Answer;
import org.paggarwal.rancherscheduler.models.Task;
import org.paggarwal.rancherscheduler.service.db.TaskService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by paggarwal on 3/7/16.
 */
@Service
public class TaskHandler {
    @Inject
    private TaskService taskService;

    public RequestHandlerWrapper<EmptyPayload> list() {
        return new RequestHandlerWrapper<EmptyPayload>(EmptyPayload.class,(value, urlParams) -> {
            return new Answer(200,taskService.list());
        });
    }

    public RequestHandlerWrapper<Task> create() {
        return new RequestHandlerWrapper<Task>(Task.class,(value, urlParams) -> {
            return new Answer(201,ImmutableMap.of("id",taskService.create(value)));
        });
    }

    public RequestHandlerWrapper<EmptyPayload> delete() {
        return new RequestHandlerWrapper<EmptyPayload>(EmptyPayload.class,(value, urlParams) -> {
            return new Answer(200,ImmutableMap.of("status", taskService.delete((int) Integer.parseInt(urlParams.get("id")))));
        });
    }
}
