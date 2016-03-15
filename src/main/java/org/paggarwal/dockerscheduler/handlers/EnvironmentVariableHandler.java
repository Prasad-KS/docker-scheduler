package org.paggarwal.dockerscheduler.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;
import org.paggarwal.dockerscheduler.Answer;
import org.paggarwal.dockerscheduler.RequestHandlerWrapper;
import org.paggarwal.dockerscheduler.models.EnvironmentVariable;
import org.paggarwal.dockerscheduler.models.ValidableList;
import org.paggarwal.dockerscheduler.service.EnvironmentVariableService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by paggarwal on 3/7/16.
 */
@Service
public class EnvironmentVariableHandler {
    @Inject
    private EnvironmentVariableService environmentVariableService;

    public RequestHandlerWrapper<EmptyPayload> list() {
        return new RequestHandlerWrapper<EmptyPayload>(EmptyPayload.TYPE_REFERENCE, (value, urlParams) -> {
            return new Answer(200, environmentVariableService.list());
        });
    }

    public RequestHandlerWrapper<ValidableList<EnvironmentVariable>> create() {
        return new RequestHandlerWrapper<ValidableList<EnvironmentVariable>>(new TypeReference<ValidableList<EnvironmentVariable>>(){},(value, urlParams) -> {
            return new Answer(201,ImmutableMap.of("status",environmentVariableService.create(value)));
        });
    }

    public RequestHandlerWrapper<EnvironmentVariable> update() {
        return new RequestHandlerWrapper<EnvironmentVariable>(EnvironmentVariable.TYPE_REFERENCE,(value, urlParams) -> {
            return new Answer(201,ImmutableMap.of("status",environmentVariableService.update(value)));
        });
    }

    public RequestHandlerWrapper<EmptyPayload> delete() {
        return new RequestHandlerWrapper<EmptyPayload>(EmptyPayload.TYPE_REFERENCE,(value, urlParams) -> {
            return new Answer(200,ImmutableMap.of("status", environmentVariableService.delete((int) Integer.parseInt(urlParams.get(":id")))));
        });
    }
}
