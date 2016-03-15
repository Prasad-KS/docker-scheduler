package org.paggarwal.dockerscheduler.service.db;

import org.jooq.DSLContext;
import org.paggarwal.dockerscheduler.models.Task;
import org.paggarwal.dockerscheduler.service.TaskService;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.paggarwal.dockerscheduler.generated.tables.Tasks.TASKS;

/**
 * Created by paggarwal on 3/2/16.
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Inject
    private DSLContext dsl;

    @Inject
    private Scheduler scheduler;

    @Transactional(readOnly = true)
    public List<Task> list() {
        return dsl.selectFrom(TASKS).where(TASKS.TYPE.equal(0)).fetchInto(Task.class);
    }

    @Transactional(readOnly = true)
    public Optional<Task> get(int id) {
        return dsl.selectFrom(TASKS).where(TASKS.ID.equal(id)).fetchInto(Task.class).stream().findFirst();
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer create(Task task) {
        return dsl.insertInto(TASKS).columns(TASKS.NAME
                , TASKS.IMAGE, TASKS.COMMAND).values(task.getName(), task.getImage(), task.getCommand()).returning(TASKS.ID).fetchOne().getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(int id) {
        return dsl.delete(TASKS).where(TASKS.ID.equal(id)).execute() > 0;
    }
}
