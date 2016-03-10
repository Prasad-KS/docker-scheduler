package org.paggarwal.dockerscheduler.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by paggarwal on 3/7/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduledTask {
    private final long id;
    private final Task task;
    private String cron;

    @JsonCreator
    public ScheduledTask(@JsonProperty("id") long id, @JsonProperty("task") Task task, @JsonProperty("cron") String cron) {
        this.id = id;
        this.task = task;
        this.cron = cron;
    }

    public long getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

    public String getCron() {
        return cron;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScheduledTask)) return false;
        ScheduledTask that = (ScheduledTask) o;
        return getId() == that.getId() &&
                Objects.equal(getTask(), that.getTask()) &&
                Objects.equal(getCron(), that.getCron());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getTask(), getCron());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("task", task)
                .add("cron", cron)
                .toString();
    }


    public static class Builder {
        private long id;
        private Task task;
        private String cron;

        private Builder() {
        }

        public static Builder aScheduledTask() {
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

        public Builder withCron(String cron) {
            this.cron = cron;
            return this;
        }

        public Builder but() {
            return aScheduledTask().withId(id).withTask(task).withCron(cron);
        }

        public ScheduledTask build() {
            ScheduledTask scheduledTask = new ScheduledTask(id, task, cron);
            return scheduledTask;
        }
    }
}
