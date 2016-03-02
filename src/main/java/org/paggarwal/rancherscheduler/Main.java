package org.paggarwal.rancherscheduler;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.FileSystemResourceAccessor;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
/**
 * Created by paggarwal on 2/22/16.
 */
@Configuration
@ComponentScan("org.paggarwal.rancherscheduler")
public class Main {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        java.sql.Connection c = ctx.getBean(DataSource.class).getConnection();
        Liquibase liquibase = null;
        try {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c));
            liquibase = new Liquibase("src/main/db/changelogs.xml", new FileSystemResourceAccessor(), database);
            Contexts contexts = new Contexts();
            liquibase.update(contexts);
        } finally {
            if (c != null) {
                try {
                    c.rollback();
                    c.close();
                } catch (SQLException e) {
                    //nothing to do
                }
            }
        }
        ctx.getBean(WebServer.class).run();
    }
}
