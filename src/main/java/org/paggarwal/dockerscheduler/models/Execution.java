package org.paggarwal.dockerscheduler.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Date;
import java.util.Map;

/**
 * Created by paggarwal on 3/15/16.
 */
public class Execution {
    public enum Status {
        EXECUTING,
        SUCCEDED,
        FAILED
    }

    private long id;
    private Task task;
    private String stdout;
    private String stderr;
    private Map<String,String> environmentVariables;
    private Status status;
    private Date startedOn;
    private Date endedOn;

    public Execution(long id, Task task, String stdout, String stderr, Map<String, String> environmentVariables, Status status, Date startedOn, Date endedOn) {
        this.id = id;
        this.task = task;
        this.stdout = stdout;
        this.stderr = stderr;
        this.environmentVariables = environmentVariables;
        this.status = status;
        this.startedOn = startedOn;
        this.endedOn = endedOn;
    }

    public long getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

    public String getStdout() {
        return stdout;
    }

    public String getStderr() {
        return stderr;
    }

    public Map<String, String> getEnvironmentVariables() {
        return environmentVariables;
    }

    public Status getStatus() {
        return status;
    }

    public Date getStartedOn() {
        return startedOn;
    }

    public Date getEndedOn() {
        return endedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Execution)) return false;
        Execution execution = (Execution) o;
        return id == execution.id &&
                Objects.equal(task, execution.task) &&
                Objects.equal(stdout, execution.stdout) &&
                Objects.equal(stderr, execution.stderr) &&
                Objects.equal(environmentVariables, execution.environmentVariables) &&
                status == execution.status &&
                Objects.equal(startedOn, execution.startedOn) &&
                Objects.equal(endedOn, execution.endedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, task, stdout, stderr, environmentVariables, status, startedOn, endedOn);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("task", task)
                .add("stdout", stdout)
                .add("stderr", stderr)
                .add("environmentVariables", environmentVariables)
                .add("status", status)
                .add("startedOn", startedOn)
                .add("endedOn", endedOn)
                .toString();
    }


    public static class Builder {
        private long id;
        private Task task;
        private String stdout;
        private String stderr;
        private Map<String,String> environmentVariables;
        private Status status;
        private Date startedOn;
        private Date endedOn;

        private Builder() {
        }

        public static Builder anExecution() {
            return new Builder();
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withTask(Task task) {
            this.task = task;
            return this;
        }

        public Builder withStdout(String stdout) {
            this.stdout = stdout;
            return this;
        }

        public Builder withStderr(String stderr) {
            this.stderr = stderr;
            return this;
        }

        public Builder withEnvironmentVariables(Map<String, String> environmentVariables) {
            this.environmentVariables = environmentVariables;
            return this;
        }

        public Builder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder withStartedOn(Date startedOn) {
            this.startedOn = startedOn;
            return this;
        }

        public Builder withEndedOn(Date endedOn) {
            this.endedOn = endedOn;
            return this;
        }

        public Builder but() {
            return anExecution().withId(id).withTask(task).withStdout(stdout).withStderr(stderr).withEnvironmentVariables(environmentVariables).withStatus(status).withStartedOn(startedOn).withEndedOn(endedOn);
        }

        public Execution build() {
            Execution execution = new Execution(id, task, stdout, stderr, environmentVariables, status, startedOn, endedOn);
            return execution;
        }
    }
}
