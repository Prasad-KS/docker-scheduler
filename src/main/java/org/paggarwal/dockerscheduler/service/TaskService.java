package org.paggarwal.dockerscheduler.service;

import org.paggarwal.dockerscheduler.models.Task;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.paggarwal.dockerscheduler.generated.tables.Tasks.TASKS;

/**
 * Created by paggarwal on 3/14/16.
 */
public interface TaskService {
    List<Task> list();

    Optional<Task> get(int id);

    Integer create(Task task);

    boolean delete(int id);
}
