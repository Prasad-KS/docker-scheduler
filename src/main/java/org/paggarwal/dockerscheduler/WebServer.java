package org.paggarwal.dockerscheduler;

import com.google.common.io.CharStreams;
import org.paggarwal.dockerscheduler.handlers.EnvironmentVariableHandler;
import org.paggarwal.dockerscheduler.handlers.ScheduledTaskHandler;
import org.paggarwal.dockerscheduler.handlers.TaskHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.InputStreamReader;

import static spark.Spark.*;

/**
 * Created by paggarwal on 2/29/16.
 */
@Component
public class WebServer {
    @Inject
    private TaskHandler taskHandler;

    @Inject
    private ScheduledTaskHandler scheduledTaskHandler;

    @Inject
    private EnvironmentVariableHandler environmentVariableHandler;

    public void run() {
        port(8080);
        staticFileLocation("/public");
        setupRoutes();
    }

    private void setupRoutes() {
        // MainApp
        get("/", (request, response) -> CharStreams.toString(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("public/index.html"))));

        // Tasks
        get("/v1/tasks",taskHandler.list());
        post("/v1/tasks",taskHandler.create());
        delete("/v1/tasks/:id",taskHandler.delete());

        // Scheduled Tasks
        get("/v1/scheduledtasks",scheduledTaskHandler.list());
        post("/v1/scheduledtasks",scheduledTaskHandler.create());
        delete("/v1/scheduledtasks/:id",scheduledTaskHandler.delete());

        // Environment Variables
        get("/v1/environmentvariables",environmentVariableHandler.list());
        post("/v1/environmentvariables",environmentVariableHandler.create());
        put("/v1/environmentvariables/:id",environmentVariableHandler.update());
        delete("/v1/environmentvariables/:id",environmentVariableHandler.delete());
    }
}
