/**
 * This class is generated by jOOQ
 */
package org.paggarwal.dockerscheduler.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record10;
import org.jooq.Record3;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;
import org.paggarwal.dockerscheduler.db.tables.QrtzJobDetails;


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
public class QrtzJobDetailsRecord extends UpdatableRecordImpl<QrtzJobDetailsRecord> implements Record10<String, String, String, String, String, Boolean, Boolean, Boolean, Boolean, byte[]> {

	private static final long serialVersionUID = -782742820;

	/**
	 * Setter for <code>dockerscheduler.QRTZ_JOB_DETAILS.SCHED_NAME</code>.
	 */
	public void setSchedName(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>dockerscheduler.QRTZ_JOB_DETAILS.SCHED_NAME</code>.
	 */
	public String getSchedName() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>dockerscheduler.QRTZ_JOB_DETAILS.JOB_NAME</code>.
	 */
	public void setJobName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>dockerscheduler.QRTZ_JOB_DETAILS.JOB_NAME</code>.
	 */
	public String getJobName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>dockerscheduler.QRTZ_JOB_DETAILS.JOB_GROUP</code>.
	 */
	public void setJobGroup(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>dockerscheduler.QRTZ_JOB_DETAILS.JOB_GROUP</code>.
	 */
	public String getJobGroup() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>dockerscheduler.QRTZ_JOB_DETAILS.DESCRIPTION</code>.
	 */
	public void setDescription(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>dockerscheduler.QRTZ_JOB_DETAILS.DESCRIPTION</code>.
	 */
	public String getDescription() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>dockerscheduler.QRTZ_JOB_DETAILS.JOB_CLASS_NAME</code>.
	 */
	public void setJobClassName(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>dockerscheduler.QRTZ_JOB_DETAILS.JOB_CLASS_NAME</code>.
	 */
	public String getJobClassName() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>dockerscheduler.QRTZ_JOB_DETAILS.IS_DURABLE</code>.
	 */
	public void setIsDurable(Boolean value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>dockerscheduler.QRTZ_JOB_DETAILS.IS_DURABLE</code>.
	 */
	public Boolean getIsDurable() {
		return (Boolean) getValue(5);
	}

	/**
	 * Setter for <code>dockerscheduler.QRTZ_JOB_DETAILS.IS_NONCONCURRENT</code>.
	 */
	public void setIsNonconcurrent(Boolean value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>dockerscheduler.QRTZ_JOB_DETAILS.IS_NONCONCURRENT</code>.
	 */
	public Boolean getIsNonconcurrent() {
		return (Boolean) getValue(6);
	}

	/**
	 * Setter for <code>dockerscheduler.QRTZ_JOB_DETAILS.IS_UPDATE_DATA</code>.
	 */
	public void setIsUpdateData(Boolean value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>dockerscheduler.QRTZ_JOB_DETAILS.IS_UPDATE_DATA</code>.
	 */
	public Boolean getIsUpdateData() {
		return (Boolean) getValue(7);
	}

	/**
	 * Setter for <code>dockerscheduler.QRTZ_JOB_DETAILS.REQUESTS_RECOVERY</code>.
	 */
	public void setRequestsRecovery(Boolean value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>dockerscheduler.QRTZ_JOB_DETAILS.REQUESTS_RECOVERY</code>.
	 */
	public Boolean getRequestsRecovery() {
		return (Boolean) getValue(8);
	}

	/**
	 * Setter for <code>dockerscheduler.QRTZ_JOB_DETAILS.JOB_DATA</code>.
	 */
	public void setJobData(byte[] value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>dockerscheduler.QRTZ_JOB_DETAILS.JOB_DATA</code>.
	 */
	public byte[] getJobData() {
		return (byte[]) getValue(9);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record3<String, String, String> key() {
		return (Record3) super.key();
	}

	// -------------------------------------------------------------------------
	// Record10 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row10<String, String, String, String, String, Boolean, Boolean, Boolean, Boolean, byte[]> fieldsRow() {
		return (Row10) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row10<String, String, String, String, String, Boolean, Boolean, Boolean, Boolean, byte[]> valuesRow() {
		return (Row10) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return QrtzJobDetails.QRTZ_JOB_DETAILS.SCHED_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return QrtzJobDetails.QRTZ_JOB_DETAILS.JOB_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return QrtzJobDetails.QRTZ_JOB_DETAILS.JOB_GROUP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return QrtzJobDetails.QRTZ_JOB_DETAILS.DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return QrtzJobDetails.QRTZ_JOB_DETAILS.JOB_CLASS_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Boolean> field6() {
		return QrtzJobDetails.QRTZ_JOB_DETAILS.IS_DURABLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Boolean> field7() {
		return QrtzJobDetails.QRTZ_JOB_DETAILS.IS_NONCONCURRENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Boolean> field8() {
		return QrtzJobDetails.QRTZ_JOB_DETAILS.IS_UPDATE_DATA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Boolean> field9() {
		return QrtzJobDetails.QRTZ_JOB_DETAILS.REQUESTS_RECOVERY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<byte[]> field10() {
		return QrtzJobDetails.QRTZ_JOB_DETAILS.JOB_DATA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getSchedName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getJobName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getJobGroup();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getJobClassName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean value6() {
		return getIsDurable();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean value7() {
		return getIsNonconcurrent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean value8() {
		return getIsUpdateData();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean value9() {
		return getRequestsRecovery();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] value10() {
		return getJobData();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QrtzJobDetailsRecord value1(String value) {
		setSchedName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QrtzJobDetailsRecord value2(String value) {
		setJobName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QrtzJobDetailsRecord value3(String value) {
		setJobGroup(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QrtzJobDetailsRecord value4(String value) {
		setDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QrtzJobDetailsRecord value5(String value) {
		setJobClassName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QrtzJobDetailsRecord value6(Boolean value) {
		setIsDurable(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QrtzJobDetailsRecord value7(Boolean value) {
		setIsNonconcurrent(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QrtzJobDetailsRecord value8(Boolean value) {
		setIsUpdateData(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QrtzJobDetailsRecord value9(Boolean value) {
		setRequestsRecovery(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QrtzJobDetailsRecord value10(byte[] value) {
		setJobData(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QrtzJobDetailsRecord values(String value1, String value2, String value3, String value4, String value5, Boolean value6, Boolean value7, Boolean value8, Boolean value9, byte[] value10) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		value9(value9);
		value10(value10);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached QrtzJobDetailsRecord
	 */
	public QrtzJobDetailsRecord() {
		super(QrtzJobDetails.QRTZ_JOB_DETAILS);
	}

	/**
	 * Create a detached, initialised QrtzJobDetailsRecord
	 */
	public QrtzJobDetailsRecord(String schedName, String jobName, String jobGroup, String description, String jobClassName, Boolean isDurable, Boolean isNonconcurrent, Boolean isUpdateData, Boolean requestsRecovery, byte[] jobData) {
		super(QrtzJobDetails.QRTZ_JOB_DETAILS);

		setValue(0, schedName);
		setValue(1, jobName);
		setValue(2, jobGroup);
		setValue(3, description);
		setValue(4, jobClassName);
		setValue(5, isDurable);
		setValue(6, isNonconcurrent);
		setValue(7, isUpdateData);
		setValue(8, requestsRecovery);
		setValue(9, jobData);
	}
}