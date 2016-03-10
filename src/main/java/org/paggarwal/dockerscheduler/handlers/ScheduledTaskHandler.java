package org.paggarwal.dockerscheduler.handlers;

import com.google.common.collect.ImmutableMap;
import org.paggarwal.dockerscheduler.Answer;
import org.paggarwal.dockerscheduler.RequestHandlerWrapper;
import org.paggarwal.dockerscheduler.models.ScheduledTask;
import org.paggarwal.dockerscheduler.models.Task;
import org.paggarwal.dockerscheduler.service.db.ScheduledTaskService;
import org.paggarwal.dockerscheduler.service.db.TaskService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by paggarwal on 3/7/16.
 */
@Service
public class ScheduledTaskHandler {
    @Inject
    private ScheduledTaskService scheduledTaskService;

    public RequestHandlerWrapper<EmptyPayload> list() {
        return new RequestHandlerWrapper<EmptyPayload>(EmptyPayload.class,(value, urlParams) -> {
            return new Answer(200,scheduledTaskService.list());
        });
    }

    public RequestHandlerWrapper<ScheduledTask> create() {
        return new RequestHandlerWrapper<ScheduledTask>(ScheduledTask.class,(value, urlParams) -> {
            return new Answer(201,ImmutableMap.of("id",scheduledTaskService.create(value)));
        });
    }

    public RequestHandlerWrapper<EmptyPayload> delete() {
        return new RequestHandlerWrapper<EmptyPayload>(EmptyPayload.class,(value, urlParams) -> {
            return new Answer(200,ImmutableMap.of("status", scheduledTaskService.delete((int) Integer.parseInt(urlParams.get(":id")))));
        });
    }
}
