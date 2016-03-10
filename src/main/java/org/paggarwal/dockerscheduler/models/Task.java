package org.paggarwal.dockerscheduler.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.paggarwal.dockerscheduler.Validable;

import java.util.Date;

/**
 * Created by paggarwal on 3/4/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task implements Validable {

    private long id;
    private String name;
    private Date createdOn;
    private String image;
    private String command;
    private int type;
    private long success;
    private long failed;


    @JsonCreator
    public Task(@JsonProperty("id") long id,
                @JsonProperty("name") String name,
                @JsonProperty("createdOn") Date createdOn,
                @JsonProperty("image") String image,
                @JsonProperty("command") String command,
                @JsonProperty("type") int type,
                @JsonProperty("success") long success,
                @JsonProperty("failed") long failed) {
        this.id = id;
        this.name = name;
        this.createdOn = createdOn;
        this.image = image;
        this.command = command;
        this.type = type;
        this.success = success;
        this.failed = failed;
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

    public int getType() {
        return type;
    }

    public long getSuccess() {
        return success;
    }

    public long getFailed() {
        return failed;
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
                Objects.equal(getCommand(), task.getCommand());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getName(), getCreatedOn(), getImage(), getCommand(), getType(), getSuccess(), getFailed());
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
        private int type;
        private long success;
        private long failed;

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

        public Builder withType(int type) {
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

        public Builder but() {
            return aTask().withId(id).withName(name).withCreatedOn(createdOn).withImage(image).withCommand(command).withType(type).withSuccess(success).withFailed(failed);
        }

        public Task build() {
            Task task = new Task(id, name, createdOn, image, command, type, success, failed);
            return task;
        }
    }
}
