package org.paggarwal.dockerscheduler;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceUtils;

import java.sql.SQLException;
/**
 * Created by paggarwal on 2/22/16.
 */
@Configuration
@ComponentScan("org.paggarwal.dockerscheduler")
public class Main {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        ctx.getBean(WebServer.class).run();
    }

    private static void updateDatabase(AnnotationConfigApplicationContext ctx) throws SQLException, LiquibaseException {
        DataSource ds = ctx.getBean(DataSource.class);
        java.sql.Connection c = null;
        try {
            c = DataSourceUtils.doGetConnection(ds);
            Liquibase liquibase = null;
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c));
            liquibase = new Liquibase("src/main/impl/changelogs.xml", new FileSystemResourceAccessor(), database);
            Contexts contexts = new Contexts();
            liquibase.update(contexts);
        } finally {
            DataSourceUtils.doCloseConnection(c,ds);
        }
    }
}
