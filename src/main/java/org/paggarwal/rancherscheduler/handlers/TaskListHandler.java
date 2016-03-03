package org.paggarwal.rancherscheduler.handlers;

import org.paggarwal.rancherscheduler.AbstractRequestHandler;
import org.paggarwal.rancherscheduler.Answer;
import org.paggarwal.rancherscheduler.service.db.TaskService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by paggarwal on 2/29/16.
 */
@Service
public class TaskListHandler extends AbstractRequestHandler<EmptyPayload> {

    @Inject
    private TaskService taskService;

    public TaskListHandler() {
        super(EmptyPayload.class);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map<String, String> urlParams) {
        return new Answer(200, dataToJson(taskService.list()));
    }
}
