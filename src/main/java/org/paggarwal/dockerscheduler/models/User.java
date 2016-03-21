package org.paggarwal.dockerscheduler.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by paggarwal on 3/21/16.
 */
public class User {
    private int id;
    private int githubId;
    private String name;

    public User(int id, int githubId, String name) {
        this.id = id;
        this.githubId = githubId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getGithubId() {
        return githubId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                getGithubId() == user.getGithubId() &&
                Objects.equal(getName(), user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getGithubId(), getName());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("githubId", githubId)
                .add("name", name)
                .toString();
    }


    public static class Builder {
        private int id;
        private int githubId;
        private String name;

        private Builder() {
        }

        public static Builder anUser() {
            return new Builder();
        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withGithubId(int githubId) {
            this.githubId = githubId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder but() {
            return anUser().withId(id).withGithubId(githubId).withName(name);
        }

        public User build() {
            User user = new User(id, githubId, name);
            return user;
        }
    }
}
