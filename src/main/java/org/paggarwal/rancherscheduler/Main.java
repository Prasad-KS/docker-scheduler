package org.paggarwal.rancherscheduler;

import com.google.common.io.CharStreams;
import org.paggarwal.rancherscheduler.handlers.EmptyPayload;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.InputStreamReader;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;
/**
 * Created by paggarwal on 2/22/16.
 */
@Configuration
@ComponentScan("org.paggarwal.rancherscheduler")
public class Main {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        ctx.registerShutdownHook();
        ctx.getBean(WebServer.class).run();
    }
}
