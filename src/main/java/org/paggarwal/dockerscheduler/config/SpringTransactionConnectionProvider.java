package org.paggarwal.dockerscheduler.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.jooq.ConnectionProvider;
import org.jooq.exception.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by paggarwal on 3/7/16.
 */
public class SpringTransactionConnectionProvider implements ConnectionProvider {
    private final DataSource ds;

    public SpringTransactionConnectionProvider(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public Connection acquire() throws DataAccessException {
        try {
            return DataSourceUtils.doGetConnection(ds);
        } catch (SQLException e) {
            throw new DataAccessException("Error getting data from data source " + ds, e);
        }
    }

    @Override
    public void release(Connection connection) throws DataAccessException {
        try {
            DataSourceUtils.doReleaseConnection(connection, ds);
        } catch (SQLException e) {
            throw new DataAccessException("Error closing connection " + connection, e);
        }
    }
}
