/**
 * This class is generated by jOOQ
 */
package org.paggarwal.dockerscheduler.db;


import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;
import org.paggarwal.dockerscheduler.db.tables.EnvironmentVariables;
import org.paggarwal.dockerscheduler.db.tables.Executions;
import org.paggarwal.dockerscheduler.db.tables.QrtzBlobTriggers;
import org.paggarwal.dockerscheduler.db.tables.QrtzCalendars;
import org.paggarwal.dockerscheduler.db.tables.QrtzCronTriggers;
import org.paggarwal.dockerscheduler.db.tables.QrtzFiredTriggers;
import org.paggarwal.dockerscheduler.db.tables.QrtzJobDetails;
import org.paggarwal.dockerscheduler.db.tables.QrtzLocks;
import org.paggarwal.dockerscheduler.db.tables.QrtzPausedTriggerGrps;
import org.paggarwal.dockerscheduler.db.tables.QrtzSchedulerState;
import org.paggarwal.dockerscheduler.db.tables.QrtzSimpleTriggers;
import org.paggarwal.dockerscheduler.db.tables.QrtzSimpropTriggers;
import org.paggarwal.dockerscheduler.db.tables.QrtzTriggers;
import org.paggarwal.dockerscheduler.db.tables.Tasks;
import org.paggarwal.dockerscheduler.db.tables.Users;
import org.paggarwal.dockerscheduler.db.tables.records.EnvironmentVariablesRecord;
import org.paggarwal.dockerscheduler.db.tables.records.ExecutionsRecord;
import org.paggarwal.dockerscheduler.db.tables.records.QrtzBlobTriggersRecord;
import org.paggarwal.dockerscheduler.db.tables.records.QrtzCalendarsRecord;
import org.paggarwal.dockerscheduler.db.tables.records.QrtzCronTriggersRecord;
import org.paggarwal.dockerscheduler.db.tables.records.QrtzFiredTriggersRecord;
import org.paggarwal.dockerscheduler.db.tables.records.QrtzJobDetailsRecord;
import org.paggarwal.dockerscheduler.db.tables.records.QrtzLocksRecord;
import org.paggarwal.dockerscheduler.db.tables.records.QrtzPausedTriggerGrpsRecord;
import org.paggarwal.dockerscheduler.db.tables.records.QrtzSchedulerStateRecord;
import org.paggarwal.dockerscheduler.db.tables.records.QrtzSimpleTriggersRecord;
import org.paggarwal.dockerscheduler.db.tables.records.QrtzSimpropTriggersRecord;
import org.paggarwal.dockerscheduler.db.tables.records.QrtzTriggersRecord;
import org.paggarwal.dockerscheduler.db.tables.records.TasksRecord;
import org.paggarwal.dockerscheduler.db.tables.records.UsersRecord;


/**
 * A class modelling foreign key relationships between tables of the <code>dockerscheduler</code> 
 * schema
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------

	public static final Identity<EnvironmentVariablesRecord, Integer> IDENTITY_ENVIRONMENT_VARIABLES = Identities0.IDENTITY_ENVIRONMENT_VARIABLES;
	public static final Identity<ExecutionsRecord, Integer> IDENTITY_EXECUTIONS = Identities0.IDENTITY_EXECUTIONS;
	public static final Identity<TasksRecord, Integer> IDENTITY_TASKS = Identities0.IDENTITY_TASKS;
	public static final Identity<UsersRecord, Integer> IDENTITY_USERS = Identities0.IDENTITY_USERS;

	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final UniqueKey<EnvironmentVariablesRecord> KEY_ENVIRONMENT_VARIABLES_PRIMARY = UniqueKeys0.KEY_ENVIRONMENT_VARIABLES_PRIMARY;
	public static final UniqueKey<EnvironmentVariablesRecord> KEY_ENVIRONMENT_VARIABLES_U_ENVIRONMENT_VARIABLE_NAME = UniqueKeys0.KEY_ENVIRONMENT_VARIABLES_U_ENVIRONMENT_VARIABLE_NAME;
	public static final UniqueKey<ExecutionsRecord> KEY_EXECUTIONS_PRIMARY = UniqueKeys0.KEY_EXECUTIONS_PRIMARY;
	public static final UniqueKey<QrtzBlobTriggersRecord> KEY_QRTZ_BLOB_TRIGGERS_PRIMARY = UniqueKeys0.KEY_QRTZ_BLOB_TRIGGERS_PRIMARY;
	public static final UniqueKey<QrtzCalendarsRecord> KEY_QRTZ_CALENDARS_PRIMARY = UniqueKeys0.KEY_QRTZ_CALENDARS_PRIMARY;
	public static final UniqueKey<QrtzCronTriggersRecord> KEY_QRTZ_CRON_TRIGGERS_PRIMARY = UniqueKeys0.KEY_QRTZ_CRON_TRIGGERS_PRIMARY;
	public static final UniqueKey<QrtzFiredTriggersRecord> KEY_QRTZ_FIRED_TRIGGERS_PRIMARY = UniqueKeys0.KEY_QRTZ_FIRED_TRIGGERS_PRIMARY;
	public static final UniqueKey<QrtzJobDetailsRecord> KEY_QRTZ_JOB_DETAILS_PRIMARY = UniqueKeys0.KEY_QRTZ_JOB_DETAILS_PRIMARY;
	public static final UniqueKey<QrtzLocksRecord> KEY_QRTZ_LOCKS_PRIMARY = UniqueKeys0.KEY_QRTZ_LOCKS_PRIMARY;
	public static final UniqueKey<QrtzPausedTriggerGrpsRecord> KEY_QRTZ_PAUSED_TRIGGER_GRPS_PRIMARY = UniqueKeys0.KEY_QRTZ_PAUSED_TRIGGER_GRPS_PRIMARY;
	public static final UniqueKey<QrtzSchedulerStateRecord> KEY_QRTZ_SCHEDULER_STATE_PRIMARY = UniqueKeys0.KEY_QRTZ_SCHEDULER_STATE_PRIMARY;
	public static final UniqueKey<QrtzSimpleTriggersRecord> KEY_QRTZ_SIMPLE_TRIGGERS_PRIMARY = UniqueKeys0.KEY_QRTZ_SIMPLE_TRIGGERS_PRIMARY;
	public static final UniqueKey<QrtzSimpropTriggersRecord> KEY_QRTZ_SIMPROP_TRIGGERS_PRIMARY = UniqueKeys0.KEY_QRTZ_SIMPROP_TRIGGERS_PRIMARY;
	public static final UniqueKey<QrtzTriggersRecord> KEY_QRTZ_TRIGGERS_PRIMARY = UniqueKeys0.KEY_QRTZ_TRIGGERS_PRIMARY;
	public static final UniqueKey<TasksRecord> KEY_TASKS_PRIMARY = UniqueKeys0.KEY_TASKS_PRIMARY;
	public static final UniqueKey<TasksRecord> KEY_TASKS_UK_TASKS_NAME = UniqueKeys0.KEY_TASKS_UK_TASKS_NAME;
	public static final UniqueKey<UsersRecord> KEY_USERS_PRIMARY = UniqueKeys0.KEY_USERS_PRIMARY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------

	public static final ForeignKey<ExecutionsRecord, TasksRecord> FK_TASKS_EXECUTIONS = ForeignKeys0.FK_TASKS_EXECUTIONS;
	public static final ForeignKey<QrtzCronTriggersRecord, QrtzTriggersRecord> FK_QRTZ_CRON_TRIGGERS_QRTZ_TRIGGERS = ForeignKeys0.FK_QRTZ_CRON_TRIGGERS_QRTZ_TRIGGERS;
	public static final ForeignKey<QrtzSimpleTriggersRecord, QrtzTriggersRecord> FK_QRTZ_SIMPLE_TRIGGERS_QRTZ_TRIGGERS = ForeignKeys0.FK_QRTZ_SIMPLE_TRIGGERS_QRTZ_TRIGGERS;
	public static final ForeignKey<QrtzSimpropTriggersRecord, QrtzTriggersRecord> FK_QRTZ_SIMPROP_TRIGGERS_QRTZ_TRIGGERS = ForeignKeys0.FK_QRTZ_SIMPROP_TRIGGERS_QRTZ_TRIGGERS;
	public static final ForeignKey<QrtzTriggersRecord, QrtzJobDetailsRecord> FK_QRTZ_TRIGGERS_QRTZ_JOB_DETAILS = ForeignKeys0.FK_QRTZ_TRIGGERS_QRTZ_JOB_DETAILS;

	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class Identities0 extends AbstractKeys {
		public static Identity<EnvironmentVariablesRecord, Integer> IDENTITY_ENVIRONMENT_VARIABLES = createIdentity(EnvironmentVariables.ENVIRONMENT_VARIABLES, EnvironmentVariables.ENVIRONMENT_VARIABLES.ID);
		public static Identity<ExecutionsRecord, Integer> IDENTITY_EXECUTIONS = createIdentity(Executions.EXECUTIONS, Executions.EXECUTIONS.ID);
		public static Identity<TasksRecord, Integer> IDENTITY_TASKS = createIdentity(Tasks.TASKS, Tasks.TASKS.ID);
		public static Identity<UsersRecord, Integer> IDENTITY_USERS = createIdentity(Users.USERS, Users.USERS.ID);
	}

	private static class UniqueKeys0 extends AbstractKeys {
		public static final UniqueKey<EnvironmentVariablesRecord> KEY_ENVIRONMENT_VARIABLES_PRIMARY = createUniqueKey(EnvironmentVariables.ENVIRONMENT_VARIABLES, EnvironmentVariables.ENVIRONMENT_VARIABLES.ID);
		public static final UniqueKey<EnvironmentVariablesRecord> KEY_ENVIRONMENT_VARIABLES_U_ENVIRONMENT_VARIABLE_NAME = createUniqueKey(EnvironmentVariables.ENVIRONMENT_VARIABLES, EnvironmentVariables.ENVIRONMENT_VARIABLES.NAME);
		public static final UniqueKey<ExecutionsRecord> KEY_EXECUTIONS_PRIMARY = createUniqueKey(Executions.EXECUTIONS, Executions.EXECUTIONS.ID);
		public static final UniqueKey<QrtzBlobTriggersRecord> KEY_QRTZ_BLOB_TRIGGERS_PRIMARY = createUniqueKey(QrtzBlobTriggers.QRTZ_BLOB_TRIGGERS, QrtzBlobTriggers.QRTZ_BLOB_TRIGGERS.SCHED_NAME, QrtzBlobTriggers.QRTZ_BLOB_TRIGGERS.TRIGGER_NAME, QrtzBlobTriggers.QRTZ_BLOB_TRIGGERS.TRIGGER_GROUP);
		public static final UniqueKey<QrtzCalendarsRecord> KEY_QRTZ_CALENDARS_PRIMARY = createUniqueKey(QrtzCalendars.QRTZ_CALENDARS, QrtzCalendars.QRTZ_CALENDARS.SCHED_NAME, QrtzCalendars.QRTZ_CALENDARS.CALENDAR_NAME);
		public static final UniqueKey<QrtzCronTriggersRecord> KEY_QRTZ_CRON_TRIGGERS_PRIMARY = createUniqueKey(QrtzCronTriggers.QRTZ_CRON_TRIGGERS, QrtzCronTriggers.QRTZ_CRON_TRIGGERS.SCHED_NAME, QrtzCronTriggers.QRTZ_CRON_TRIGGERS.TRIGGER_NAME, QrtzCronTriggers.QRTZ_CRON_TRIGGERS.TRIGGER_GROUP);
		public static final UniqueKey<QrtzFiredTriggersRecord> KEY_QRTZ_FIRED_TRIGGERS_PRIMARY = createUniqueKey(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS, QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.SCHED_NAME, QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.ENTRY_ID);
		public static final UniqueKey<QrtzJobDetailsRecord> KEY_QRTZ_JOB_DETAILS_PRIMARY = createUniqueKey(QrtzJobDetails.QRTZ_JOB_DETAILS, QrtzJobDetails.QRTZ_JOB_DETAILS.SCHED_NAME, QrtzJobDetails.QRTZ_JOB_DETAILS.JOB_NAME, QrtzJobDetails.QRTZ_JOB_DETAILS.JOB_GROUP);
		public static final UniqueKey<QrtzLocksRecord> KEY_QRTZ_LOCKS_PRIMARY = createUniqueKey(QrtzLocks.QRTZ_LOCKS, QrtzLocks.QRTZ_LOCKS.SCHED_NAME, QrtzLocks.QRTZ_LOCKS.LOCK_NAME);
		public static final UniqueKey<QrtzPausedTriggerGrpsRecord> KEY_QRTZ_PAUSED_TRIGGER_GRPS_PRIMARY = createUniqueKey(QrtzPausedTriggerGrps.QRTZ_PAUSED_TRIGGER_GRPS, QrtzPausedTriggerGrps.QRTZ_PAUSED_TRIGGER_GRPS.SCHED_NAME, QrtzPausedTriggerGrps.QRTZ_PAUSED_TRIGGER_GRPS.TRIGGER_GROUP);
		public static final UniqueKey<QrtzSchedulerStateRecord> KEY_QRTZ_SCHEDULER_STATE_PRIMARY = createUniqueKey(QrtzSchedulerState.QRTZ_SCHEDULER_STATE, QrtzSchedulerState.QRTZ_SCHEDULER_STATE.SCHED_NAME, QrtzSchedulerState.QRTZ_SCHEDULER_STATE.INSTANCE_NAME);
		public static final UniqueKey<QrtzSimpleTriggersRecord> KEY_QRTZ_SIMPLE_TRIGGERS_PRIMARY = createUniqueKey(QrtzSimpleTriggers.QRTZ_SIMPLE_TRIGGERS, QrtzSimpleTriggers.QRTZ_SIMPLE_TRIGGERS.SCHED_NAME);
		public static final UniqueKey<QrtzSimpropTriggersRecord> KEY_QRTZ_SIMPROP_TRIGGERS_PRIMARY = createUniqueKey(QrtzSimpropTriggers.QRTZ_SIMPROP_TRIGGERS, QrtzSimpropTriggers.QRTZ_SIMPROP_TRIGGERS.SCHED_NAME, QrtzSimpropTriggers.QRTZ_SIMPROP_TRIGGERS.TRIGGER_NAME, QrtzSimpropTriggers.QRTZ_SIMPROP_TRIGGERS.TRIGGER_GROUP);
		public static final UniqueKey<QrtzTriggersRecord> KEY_QRTZ_TRIGGERS_PRIMARY = createUniqueKey(QrtzTriggers.QRTZ_TRIGGERS, QrtzTriggers.QRTZ_TRIGGERS.SCHED_NAME, QrtzTriggers.QRTZ_TRIGGERS.TRIGGER_NAME, QrtzTriggers.QRTZ_TRIGGERS.TRIGGER_GROUP);
		public static final UniqueKey<TasksRecord> KEY_TASKS_PRIMARY = createUniqueKey(Tasks.TASKS, Tasks.TASKS.ID);
		public static final UniqueKey<TasksRecord> KEY_TASKS_UK_TASKS_NAME = createUniqueKey(Tasks.TASKS, Tasks.TASKS.NAME);
		public static final UniqueKey<UsersRecord> KEY_USERS_PRIMARY = createUniqueKey(Users.USERS, Users.USERS.ID);
	}

	private static class ForeignKeys0 extends AbstractKeys {
		public static final ForeignKey<ExecutionsRecord, TasksRecord> FK_TASKS_EXECUTIONS = createForeignKey(org.paggarwal.dockerscheduler.db.Keys.KEY_TASKS_PRIMARY, Executions.EXECUTIONS, Executions.EXECUTIONS.TASK_ID);
		public static final ForeignKey<QrtzCronTriggersRecord, QrtzTriggersRecord> FK_QRTZ_CRON_TRIGGERS_QRTZ_TRIGGERS = createForeignKey(org.paggarwal.dockerscheduler.db.Keys.KEY_QRTZ_TRIGGERS_PRIMARY, QrtzCronTriggers.QRTZ_CRON_TRIGGERS, QrtzCronTriggers.QRTZ_CRON_TRIGGERS.SCHED_NAME, QrtzCronTriggers.QRTZ_CRON_TRIGGERS.TRIGGER_NAME, QrtzCronTriggers.QRTZ_CRON_TRIGGERS.TRIGGER_GROUP);
		public static final ForeignKey<QrtzSimpleTriggersRecord, QrtzTriggersRecord> FK_QRTZ_SIMPLE_TRIGGERS_QRTZ_TRIGGERS = createForeignKey(org.paggarwal.dockerscheduler.db.Keys.KEY_QRTZ_TRIGGERS_PRIMARY, QrtzSimpleTriggers.QRTZ_SIMPLE_TRIGGERS, QrtzSimpleTriggers.QRTZ_SIMPLE_TRIGGERS.SCHED_NAME, QrtzSimpleTriggers.QRTZ_SIMPLE_TRIGGERS.TRIGGER_NAME, QrtzSimpleTriggers.QRTZ_SIMPLE_TRIGGERS.TRIGGER_GROUP);
		public static final ForeignKey<QrtzSimpropTriggersRecord, QrtzTriggersRecord> FK_QRTZ_SIMPROP_TRIGGERS_QRTZ_TRIGGERS = createForeignKey(org.paggarwal.dockerscheduler.db.Keys.KEY_QRTZ_TRIGGERS_PRIMARY, QrtzSimpropTriggers.QRTZ_SIMPROP_TRIGGERS, QrtzSimpropTriggers.QRTZ_SIMPROP_TRIGGERS.SCHED_NAME, QrtzSimpropTriggers.QRTZ_SIMPROP_TRIGGERS.TRIGGER_NAME, QrtzSimpropTriggers.QRTZ_SIMPROP_TRIGGERS.TRIGGER_GROUP);
		public static final ForeignKey<QrtzTriggersRecord, QrtzJobDetailsRecord> FK_QRTZ_TRIGGERS_QRTZ_JOB_DETAILS = createForeignKey(org.paggarwal.dockerscheduler.db.Keys.KEY_QRTZ_JOB_DETAILS_PRIMARY, QrtzTriggers.QRTZ_TRIGGERS, QrtzTriggers.QRTZ_TRIGGERS.SCHED_NAME, QrtzTriggers.QRTZ_TRIGGERS.JOB_NAME, QrtzTriggers.QRTZ_TRIGGERS.JOB_GROUP);
	}
}