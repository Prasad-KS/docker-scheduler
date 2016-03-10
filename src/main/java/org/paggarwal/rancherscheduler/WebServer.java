package org.paggarwal.rancherscheduler;

import com.google.common.io.CharStreams;
import org.paggarwal.rancherscheduler.handlers.TaskHandler;
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
    }
}
