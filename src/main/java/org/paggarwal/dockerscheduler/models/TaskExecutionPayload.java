package org.paggarwal.dockerscheduler.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.paggarwal.dockerscheduler.Validable;

import java.util.List;

/**
 * Created by paggarwal on 3/21/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskExecutionPayload implements Validable {
    public static final TypeReference<TaskExecutionPayload> TYPE = new TypeReference<TaskExecutionPayload>() {};

    private List<String> payload;

    @JsonCreator
    public TaskExecutionPayload(@JsonProperty("payload") List<String> payload) {
        this.payload = payload;
    }

    public List<String> getPayload() {
        return payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskExecutionPayload)) return false;
        TaskExecutionPayload that = (TaskExecutionPayload) o;
        return Objects.equal(getPayload(), that.getPayload());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPayload());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("payload", payload)
                .toString();
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
