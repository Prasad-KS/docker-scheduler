package org.paggarwal.dockerscheduler.service;

import org.paggarwal.dockerscheduler.models.Range;
import org.paggarwal.dockerscheduler.models.Execution;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by paggarwal on 3/15/16.
 */
public interface ExecutionService {
    @Transactional(readOnly = true)
    int count(int taskId);

    List<Execution> list(int taskId, Range range);

    boolean deleteForTask(int taskId);

    boolean deleteTasksOlderThan(long timeInMillis);

    Integer create(Execution execution);

    boolean update(Execution execution);
}
