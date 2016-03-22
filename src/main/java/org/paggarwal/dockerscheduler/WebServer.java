package org.paggarwal.dockerscheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.CharStreams;
import org.paggarwal.dockerscheduler.filters.AuthFilter;
import org.paggarwal.dockerscheduler.handlers.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import javax.inject.Inject;
import java.io.InputStreamReader;
import java.util.Map;

import static com.google.common.collect.ImmutableMap.of;
import static org.apache.commons.lang.StringUtils.isNotBlank;
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

    @Inject
    private SettingsHandler settingsHandler;

    @Value("#{ systemEnvironment['GITHUB_CLIENT_ID'] }")
    private String githubClientId;

    @Value("#{ systemEnvironment['GITHUB_CLIENT_SECRET'] }")
    private String githubSecret;

    public void run() {
        port(8080);
        staticFileLocation("/public");
        setupRoutes();
    }

    private void setupRoutes() {
        // js
        get("/scripts/app.js",(request, response) -> {
            ImmutableMap.Builder builder = new ImmutableMap.Builder();
            if(isNotBlank(githubClientId)) {
                builder.put("githubClientId", githubClientId);
            }
            if(isNotBlank(githubSecret)) {
                builder.put("githubClientSecret", githubSecret);
            }

            // The hello.ftl file is located in directory:
            // src/test/resources/spark/template/freemarker
            return new ModelAndView(builder.build(), "app.js.ftl");
        }, new FreeMarkerEngine());
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

        get("/v1/settings", settingsHandler.list());
        post("/v1/settings/apiKey", settingsHandler.generateAPIKey());

        if(isNotBlank(githubClientId) || isNotBlank(githubSecret)) {
            before("/v1/*",authFilter);
        }
    }
}
