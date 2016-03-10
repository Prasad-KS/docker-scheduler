package org.paggarwal.dockerscheduler.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.jdbcjobstore.JobStoreTX;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.simpl.SimpleThreadPool;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

import static org.quartz.impl.StdSchedulerFactory.*;

/**
 * Created by paggarwal on 3/10/16.
 */
@Configuration
public class SchedulerConfig {

    @Value("#{ systemEnvironment['SCHEDULER_THREAD_COUNT'] ?: 1 }")
    private int threadCount;

    @Bean
    @Inject
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) throws Exception {
        Properties quartzProperties = new Properties();
        quartzProperties.setProperty(PROP_SCHED_INSTANCE_NAME, "DockerScheduler");
        quartzProperties.setProperty(PROP_SCHED_INSTANCE_ID, "SchedulerMaster");
        quartzProperties.setProperty(PROP_SCHED_JMX_EXPORT, Boolean.TRUE.toString());
        quartzProperties.setProperty(PROP_SCHED_SKIP_UPDATE_CHECK, Boolean.TRUE.toString());
        quartzProperties.setProperty(PROP_JOB_STORE_PREFIX + ".isClustered", Boolean.TRUE.toString());
        quartzProperties.setProperty(PROP_THREAD_POOL_PREFIX + ".threadCount", Integer.toString(threadCount));


        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setSchedulerName("DockerScheduler");
        schedulerFactoryBean.setQuartzProperties(quartzProperties);
        schedulerFactoryBean.setOverwriteExistingJobs(false);
        return schedulerFactoryBean;
    }
}
