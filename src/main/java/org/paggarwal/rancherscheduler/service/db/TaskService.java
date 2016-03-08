package org.paggarwal.rancherscheduler.service.db;

import org.jooq.DSLContext;
import org.paggarwal.rancherscheduler.models.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static org.paggarwal.rancherscheduler.generated.tables.Tasks.TASKS;

/**
 * Created by paggarwal on 3/2/16.
 */
@Service
public class TaskService {

    @Inject
    private DSLContext dsl;

    @Transactional(readOnly = true)
    public List<Task> list() {
        return dsl.selectFrom(TASKS).where(TASKS.TYPE.equal(0)).fetchInto(Task.class);
    }

    @Transactional
    public Integer create(Task task) {
        return dsl.insertInto(TASKS).columns(TASKS.NAME
                , TASKS.IMAGE, TASKS.COMMAND).values(task.getName(), task.getImage(), task.getCommand()).returning(TASKS.ID).fetchOne().getId();
    }
}
