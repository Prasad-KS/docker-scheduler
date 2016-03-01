package org.paggarwal.rancherscheduler.handlers;

import org.paggarwal.rancherscheduler.AbstractRequestHandler;
import org.paggarwal.rancherscheduler.Answer;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by paggarwal on 2/29/16.
 */
@Service
public class TaskListHandler extends AbstractRequestHandler<EmptyPayload> {

    public TaskListHandler(Class<EmptyPayload> valueClass) {
        super(valueClass);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map<String, String> urlParams) {
        return null;
    }
}
