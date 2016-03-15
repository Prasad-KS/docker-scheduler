package org.paggarwal.dockerscheduler.service;

import org.paggarwal.dockerscheduler.models.EnvironmentVariable;

import java.util.List;

/**
 * Created by paggarwal on 3/14/16.
 */
public interface EnvironmentVariableService {
    List<EnvironmentVariable> list();

    boolean create(List<EnvironmentVariable> environmentVariables);

    boolean update(EnvironmentVariable environmentVariable);

    boolean delete(int id);
}
