package org.paggarwal.dockerscheduler.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by peeyushaggarwal on 3/10/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnvironmentVariable {
    private int id;
    private String name;
    private String value;

    @JsonCreator
    public EnvironmentVariable(@JsonProperty int id,@JsonProperty String name,@JsonProperty String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnvironmentVariable that = (EnvironmentVariable) o;
        return id == that.id &&
                Objects.equal(name, that.name) &&
                Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("value", value)
                .toString();
    }


    public static class Builder {
        private int id;
        private String name;
        private String value;

        private Builder() {
        }

        public static Builder anEnvironmentVariable() {
            return new Builder();
        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public Builder but() {
            return anEnvironmentVariable().withId(id).withName(name).withValue(value);
        }

        public EnvironmentVariable build() {
            EnvironmentVariable environmentVariable = new EnvironmentVariable(id, name, value);
            return environmentVariable;
        }
    }
}
