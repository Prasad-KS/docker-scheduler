package org.paggarwal.rancherscheduler.service.db;

import org.jooq.DSLContext;
import org.paggarwal.rancherscheduler.generated.tables.Tasks;
import org.paggarwal.rancherscheduler.generated.tables.records.TasksRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

import static org.paggarwal.rancherscheduler.generated.tables.Tasks.TASKS;

/**
 * Created by paggarwal on 3/2/16.
 */
@Service
public class TaskService {

    @Inject
    DSLContext ctx;

    @Transactional(readOnly = true)
    public List<TasksRecord> list() {
        return ctx.selectFrom(TASKS).fetch();
    }
}
