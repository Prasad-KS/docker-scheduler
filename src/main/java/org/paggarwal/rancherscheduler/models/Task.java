package org.paggarwal.rancherscheduler.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Date;

/**
 * Created by paggarwal on 3/4/16.
 */
public class Task {
    private long id;
    private String name;
    private Date createdOn;
    private String image;
    private String command;
    private long success;
    private long failed;

    public Task(long id, String name, Date createdOn, String image, String command, long success, long failed) {
        this.id = id;
        this.name = name;
        this.createdOn = createdOn;
        this.image = image;
        this.command = command;
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
                getSuccess() == task.getSuccess() &&
                getFailed() == task.getFailed() &&
                Objects.equal(getName(), task.getName()) &&
                Objects.equal(getCreatedOn(), task.getCreatedOn()) &&
                Objects.equal(getImage(), task.getImage()) &&
                Objects.equal(getCommand(), task.getCommand());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getName(), getCreatedOn(), getImage(), getCommand(), getSuccess(), getFailed());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("createdOn", createdOn)
                .add("image", image)
                .add("command", command)
                .add("success", success)
                .add("failed", failed)
                .toString();
    }


    public static class Builder {
        private long id;
        private String name;
        private Date createdOn;
        private String image;
        private String command;
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

        public Builder withSuccess(long success) {
            this.success = success;
            return this;
        }

        public Builder withFailed(long failed) {
            this.failed = failed;
            return this;
        }

        public Builder but() {
            return aTask().withId(id).withName(name).withCreatedOn(createdOn).withImage(image).withCommand(command).withSuccess(success).withFailed(failed);
        }

        public Task build() {
            Task task = new Task(id, name, createdOn, image, command, success, failed);
            return task;
        }
    }
}
