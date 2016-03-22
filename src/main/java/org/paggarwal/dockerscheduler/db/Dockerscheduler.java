/**
 * This class is generated by jOOQ
 */
package org.paggarwal.dockerscheduler.db;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
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
import org.paggarwal.dockerscheduler.db.tables.Settings;
import org.paggarwal.dockerscheduler.db.tables.Tasks;
import org.paggarwal.dockerscheduler.db.tables.Users;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Dockerscheduler extends SchemaImpl {

	private static final long serialVersionUID = -499155984;

	/**
	 * The reference instance of <code>dockerscheduler</code>
	 */
	public static final Dockerscheduler DOCKERSCHEDULER = new Dockerscheduler();

	/**
	 * No further instances allowed
	 */
	private Dockerscheduler() {
		super("dockerscheduler");
	}

	@Override
	public final List<Table<?>> getTables() {
		List result = new ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final List<Table<?>> getTables0() {
		return Arrays.<Table<?>>asList(
			EnvironmentVariables.ENVIRONMENT_VARIABLES,
			Executions.EXECUTIONS,
			QrtzBlobTriggers.QRTZ_BLOB_TRIGGERS,
			QrtzCalendars.QRTZ_CALENDARS,
			QrtzCronTriggers.QRTZ_CRON_TRIGGERS,
			QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS,
			QrtzJobDetails.QRTZ_JOB_DETAILS,
			QrtzLocks.QRTZ_LOCKS,
			QrtzPausedTriggerGrps.QRTZ_PAUSED_TRIGGER_GRPS,
			QrtzSchedulerState.QRTZ_SCHEDULER_STATE,
			QrtzSimpleTriggers.QRTZ_SIMPLE_TRIGGERS,
			QrtzSimpropTriggers.QRTZ_SIMPROP_TRIGGERS,
			QrtzTriggers.QRTZ_TRIGGERS,
			Settings.SETTINGS,
			Tasks.TASKS,
			Users.USERS);
	}
}
