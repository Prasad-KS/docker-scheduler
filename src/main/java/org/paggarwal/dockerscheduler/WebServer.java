package org.paggarwal.dockerscheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import org.paggarwal.dockerscheduler.filters.AuthFilter;
import org.paggarwal.dockerscheduler.handlers.AuthHandler;
import org.paggarwal.dockerscheduler.handlers.EnvironmentVariableHandler;
import org.paggarwal.dockerscheduler.handlers.ExecutionHandler;
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

    private static final ObjectMapper mapper = new ObjectMapper();

    @Inject
    private TaskHandler taskHandler;

    @Inject
    private EnvironmentVariableHandler environmentVariableHandler;

    @Inject
    private ExecutionHandler executionHandler;

    @Inject
    private AuthHandler authHandler;

    @Inject
    private AuthFilter authFilter;

    public void run() {
        port(8080);
        staticFileLocation("/public");
        setupRoutes();
    }

    private void setupRoutes() {
        // MainApp
        get("/", (request, response) -> CharStreams.toString(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("public/index.html"))));
        post("/auth/github",authHandler);

        // Tasks
        get("/v1/tasks",taskHandler.listTasks());
        get("/v1/tasks/:id",taskHandler.getTask());
        get("/v1/scheduledtasks",taskHandler.listScheduledTasks());
        post("/v1/tasks",taskHandler.create());
        delete("/v1/tasks/:id",taskHandler.delete());
        post("/v1/tasks/:name/_execute",taskHandler.executeTask());

        // Task executions
        get("/v1/tasks/:id/executions", executionHandler.listExecutionsForATask());

        // Environment Variables
        get("/v1/environmentvariables",environmentVariableHandler.list());
        post("/v1/environmentvariables",environmentVariableHandler.create());
        put("/v1/environmentvariables/:id",environmentVariableHandler.update());
        delete("/v1/environmentvariables/:id",environmentVariableHandler.delete());

        before("/v1/*",authFilter);
    }
}
