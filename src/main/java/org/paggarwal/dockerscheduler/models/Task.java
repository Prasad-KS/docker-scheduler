package org.paggarwal.dockerscheduler.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.paggarwal.dockerscheduler.Validable;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by paggarwal on 3/4/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task implements Validable {
    public static enum Type {
        TASK,SCHEDULED_TASK;

        @JsonCreator
        public static Type forValue(String value) {
            return valueOf(value);
        }

        @JsonValue
        public String toValue() {
            return name();
        }
    }

    public static final TypeReference<Task> TYPE_REFERENCE = new TypeReference<Task>() {};

    private long id;
    private String name;
    private Date createdOn;
    private String image;
    private String command;
    private Type type;
    private long success;
    private long failed;
    private String cron;


    @JsonCreator
    public Task(@JsonProperty("id") long id,
                @JsonProperty("name") String name,
                @JsonProperty("createdOn") Date createdOn,
                @JsonProperty("image") String image,
                @JsonProperty("command") String command,
                @JsonProperty("type") Type type,
                @JsonProperty("success") long success,
                @JsonProperty("failed") long failed,
                @JsonProperty("cron") String cron) {
        this.id = id;
        this.name = name;
        this.createdOn = createdOn;
        this.image = image;
        this.command = command;
        this.type = type;
        this.success = success;
        this.failed = failed;
        this.cron = cron;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public String getImage() {
        return image;
    }

    public String getCommand() {
        return command;
    }

    public Type getType() {
        return type;
    }

    public long getSuccess() {
        return success;
    }

    public long getFailed() {
        return failed;
    }

    public String getCron() {
        return cron;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getId() == task.getId() &&
                getType() == task.getType() &&
                getSuccess() == task.getSuccess() &&
                getFailed() == task.getFailed() &&
                Objects.equal(getName(), task.getName()) &&
                Objects.equal(getCreatedOn(), task.getCreatedOn()) &&
                Objects.equal(getImage(), task.getImage()) &&
                Objects.equal(getCommand(), task.getCommand()) &&
                Objects.equal(getCron(), task.getCron());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getName(), getCreatedOn(), getImage(), getCommand(), getType(), getSuccess(), getFailed(), getCron());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("createdOn", createdOn)
                .add("image", image)
                .add("command", command)
                .add("type", type)
                .add("success", success)
                .add("failed", failed)
                .add("cron", cron)
                .toString();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public static class Builder {
        private long id;
        private String name;
        private Date createdOn;
        private String image;
        private String command;
        private Type type;
        private long success;
        private long failed;
        private String cron;

        private Builder() {
        }

        public static Builder aTask() {
            return new Builder();
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCreatedOn(Date createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public Builder withImage(String image) {
            this.image = image;
            return this;
        }

        public Builder withCommand(String command) {
            this.command = command;
            return this;
        }

        public Builder withType(Type type) {
            this.type = type;
            return this;
        }

        public Builder withSuccess(long success) {
            this.success = success;
            return this;
        }

        public Builder withFailed(long failed) {
            this.failed = failed;
            return this;
        }

        public Builder withCron(String cron) {
            this.cron = cron;
            return this;
        }

        public Builder but() {
            return aTask().withId(id).withName(name).withCreatedOn(createdOn).withImage(image).withCommand(command).withType(type).withSuccess(success).withFailed(failed).withCron(cron);
        }

        public Task build() {
            Task task = new Task(id, name, createdOn, image, command, type, success, failed, cron);
            return task;
        }
    }
}
