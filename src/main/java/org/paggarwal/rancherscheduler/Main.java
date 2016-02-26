package org.paggarwal.rancherscheduler;

import com.google.common.io.CharStreams;
import org.paggarwal.rancherscheduler.handlers.EmptyPayload;

import java.io.InputStreamReader;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;
/**
 * Created by paggarwal on 2/22/16.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        port(8080);
        staticFileLocation("/public");
        get("/", (request, response) -> CharStreams.toString(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("public/index.html"))));
    }
}
