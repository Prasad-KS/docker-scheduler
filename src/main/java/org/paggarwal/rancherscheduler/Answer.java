package org.paggarwal.rancherscheduler;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by paggarwal on 2/22/16.
 */
public class Answer {
    private final int code;
    private final Object body;

    public Answer(int code) {
        this(code, null);
    }
    public Answer(int code, Object body){
        this.code = code;
        this.body = body;
    }

    public Object getBody() {
        return body;
    }

    public int getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        Answer answer = (Answer) o;
        return getCode() == answer.getCode() &&
                Objects.equal(getBody(), answer.getBody());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCode(), getBody());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("body", body)
                .toString();
    }

    public static Answer ok(Object body) {
        return new Answer(200, body);
    }
}