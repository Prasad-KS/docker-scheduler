package org.paggarwal.dockerscheduler.service.impl;

import org.jooq.DSLContext;
import org.jooq.InsertValuesStep2;
import org.jooq.Query;
import org.paggarwal.dockerscheduler.models.EnvironmentVariable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.paggarwal.dockerscheduler.db.tables.EnvironmentVariables.ENVIRONMENT_VARIABLES;

/**
 * Created by paggarwal on 3/2/16.
 */
@Service
public class EnvironmentVariableServiceImpl implements org.paggarwal.dockerscheduler.service.EnvironmentVariableService {

    @Inject
    private DSLContext dsl;


    @Transactional(readOnly = true)
    @Override
    public List<EnvironmentVariable> list() {
        return dsl.selectFrom(ENVIRONMENT_VARIABLES).fetchInto(EnvironmentVariable.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean create(List<EnvironmentVariable> environmentVariables) {
        List<InsertValuesStep2> environmentVariablesInserts = environmentVariables.stream().map(environmentVariable -> dsl.insertInto(ENVIRONMENT_VARIABLES).columns(ENVIRONMENT_VARIABLES.NAME
                , ENVIRONMENT_VARIABLES.VALUE).values(environmentVariable.getName(), environmentVariable.getValue())).collect(Collectors.toList());
        return Arrays.stream(dsl.batch(environmentVariablesInserts).execute()).filter(value -> value <= 0).count() == 0 ;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(EnvironmentVariable environmentVariable) {
        return dsl.update(ENVIRONMENT_VARIABLES).set(ENVIRONMENT_VARIABLES.VALUE,
                environmentVariable.getValue()).where(ENVIRONMENT_VARIABLES.ID.equal(environmentVariable.getId()))
                .execute() > 0;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(int id) {
        return dsl.delete(ENVIRONMENT_VARIABLES).where(ENVIRONMENT_VARIABLES.ID.equal(id)).execute() > 0;
    }
}
