package org.paggarwal.dockerscheduler.service;

import org.paggarwal.dockerscheduler.models.Task;

import java.util.List;
import java.util.Optional;

/**
 * Created by paggarwal on 3/14/16.
 */
public interface TaskService {
    List<Task> listScheduledTasks();

    List<Task> listTasks();

    Optional<Task> get(int id);

    Integer create(Task task);

    boolean delete(int id);

    boolean execute(String taskName, List<String> payload);
}
