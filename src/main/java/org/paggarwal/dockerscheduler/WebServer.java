package org.paggarwal.dockerscheduler;

import com.google.common.io.CharStreams;
import org.paggarwal.dockerscheduler.handlers.ScheduledTaskHandler;
import org.paggarwal.dockerscheduler.handlers.TaskHandler;
import org.paggarwal.dockerscheduler.models.ScheduledTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;

import static spark.Spark.*;

/**
 * Created by paggarwal on 2/29/16.
 */
@Component
public class WebServer {
    @Autowired
    private TaskHandler taskHandler;

    @Autowired
    private ScheduledTaskHandler scheduledTaskHandler;

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

        // Tasks
        get("/v1/scheduledtasks",scheduledTaskHandler.list());
        post("/v1/scheduledtasks",scheduledTaskHandler.create());
        delete("/v1/scheduledtasks/:id",scheduledTaskHandler.delete());
    }
}
