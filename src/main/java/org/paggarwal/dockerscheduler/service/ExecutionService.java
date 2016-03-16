package org.paggarwal.dockerscheduler.service;

import org.paggarwal.dockerscheduler.models.Execution;
import org.paggarwal.dockerscheduler.models.Task;

import java.util.List;
import java.util.Optional;

/**
 * Created by paggarwal on 3/15/16.
 */
public interface ExecutionService {
    List<Execution> list(int taskId);

    boolean deleteForTask(int taskId);

    Integer create(Execution execution);

    boolean update(Execution execution);
}
