package org.paggarwal.dockerscheduler.handlers;

import com.google.common.collect.ImmutableMap;
import org.paggarwal.dockerscheduler.Answer;
import org.paggarwal.dockerscheduler.RequestHandlerWrapper;
import org.paggarwal.dockerscheduler.db.tables.Settings;
import org.paggarwal.dockerscheduler.models.Task;
import org.paggarwal.dockerscheduler.models.TaskExecutionPayload;
import org.paggarwal.dockerscheduler.service.SettingsService;
import org.paggarwal.dockerscheduler.service.TaskService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static com.google.common.collect.ImmutableMap.of;

/**
 * Created by paggarwal on 3/7/16.
 */
@Service
public class SettingsHandler {
    @Inject
    private SettingsService settingsService;

    public RequestHandlerWrapper<EmptyPayload> list() {
        return new RequestHandlerWrapper<EmptyPayload>(EmptyPayload.TYPE_REFERENCE,(value, urlParams, headers) -> {
            return new Answer(200,settingsService.list());
        });
    }

    public RequestHandlerWrapper<EmptyPayload> generateAPIKey() {
        return new RequestHandlerWrapper<EmptyPayload>(EmptyPayload.TYPE_REFERENCE,(value, urlParams, headers) -> {
            return new Answer(200, of("status", settingsService.generateAPIKey()));
        });
    }
}
