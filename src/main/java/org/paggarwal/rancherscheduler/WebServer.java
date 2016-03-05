package org.paggarwal.rancherscheduler;

import com.google.common.io.CharStreams;
import org.paggarwal.rancherscheduler.handlers.TaskListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStreamReader;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

/**
 * Created by paggarwal on 2/29/16.
 */
@Component
public class WebServer {
    @Autowired
    private TaskListHandler taskListHandler;

    public void run() {
        port(8080);
        staticFileLocation("/public");
        setupRoutes();
    }

    private void setupRoutes() {
        get("/", (request, response) -> CharStreams.toString(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("public/index.html"))));
        get("/v1/tasks",taskListHandler);
    }
}
