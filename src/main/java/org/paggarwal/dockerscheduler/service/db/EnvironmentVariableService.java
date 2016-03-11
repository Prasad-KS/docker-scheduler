package org.paggarwal.dockerscheduler.service.db;

import com.google.common.collect.Lists;
import org.jooq.DSLContext;
import org.paggarwal.dockerscheduler.models.EnvironmentVariable;
import org.paggarwal.dockerscheduler.models.Task;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.paggarwal.dockerscheduler.generated.tables.EnvironmentVariables.ENVIRONMENT_VARIABLES;

/**
 * Created by paggarwal on 3/2/16.
 */
@Service
public class EnvironmentVariableService {

    @Inject
    private DSLContext dsl;

    @Transactional(readOnly = true)
    public List<EnvironmentVariable> list() {
        return dsl.selectFrom(ENVIRONMENT_VARIABLES).fetchInto(EnvironmentVariable.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer create(EnvironmentVariable environmentVariable) {
        return dsl.insertInto(ENVIRONMENT_VARIABLES).columns(ENVIRONMENT_VARIABLES.NAME
                , ENVIRONMENT_VARIABLES.VALUE).values(environmentVariable.getName(), environmentVariable.getValue())
                .returning(ENVIRONMENT_VARIABLES.ID).fetchOne().getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean create(List<EnvironmentVariable> environmentVariables) {
        return Arrays.stream(dsl.batch(
                environmentVariables.stream().map(environmentVariable -> dsl.insertInto(ENVIRONMENT_VARIABLES).columns(ENVIRONMENT_VARIABLES.NAME
                , ENVIRONMENT_VARIABLES.VALUE).values(environmentVariable.getName(), environmentVariable.getValue())).collect(Collectors.toList())
        ).execute()).filter(value -> value <= 0).count() == 0 ;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean update(EnvironmentVariable environmentVariable) {
        return dsl.update(ENVIRONMENT_VARIABLES).set(ENVIRONMENT_VARIABLES.VALUE,
                environmentVariable.getValue()).where(ENVIRONMENT_VARIABLES.ID.equal(environmentVariable.getId()))
                .execute() > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(int id) {
        return dsl.delete(ENVIRONMENT_VARIABLES).where(ENVIRONMENT_VARIABLES.ID.equal(id)).execute() > 0;
    }
}
