package org.paggarwal.dockerscheduler.handlers;

import com.google.common.collect.ImmutableMap;
import org.paggarwal.dockerscheduler.Answer;
import org.paggarwal.dockerscheduler.RequestHandlerWrapper;
import org.paggarwal.dockerscheduler.models.Task;
import org.paggarwal.dockerscheduler.service.ExecutionService;
import org.paggarwal.dockerscheduler.service.TaskService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by paggarwal on 3/7/16.
 */
@Service
public class ExecutionHandler {
    @Inject
    private ExecutionService executionService;

    public RequestHandlerWrapper<EmptyPayload> listExecutionsForATask() {
        return new RequestHandlerWrapper<EmptyPayload>(EmptyPayload.TYPE_REFERENCE,(value, urlParams) -> {
            return new Answer(200,executionService.list(Integer.parseInt(urlParams.get(":id"))));
        });
    }
}
