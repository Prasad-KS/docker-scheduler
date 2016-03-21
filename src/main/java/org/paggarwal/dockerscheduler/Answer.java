package org.paggarwal.dockerscheduler;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Map;

/**
 * Created by paggarwal on 2/22/16.
 */
public class Answer {
    private final int code;
    private final Object body;
    private final Map<String,String> headers;

    public Answer(int code) {
        this(code, null, null);
    }
    public Answer(int code, Object body){
        this(code, body, null);
    }
    public Answer(int code, Object body, Map<String,String> headers){
        this.code = code;
        this.body = body;
        this.headers = headers;
    }

    public Object getBody() {
        return body;
    }

    public int getCode() {
        return code;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        Answer answer = (Answer) o;
        return getCode() == answer.getCode() &&
                Objects.equal(getBody(), answer.getBody()) &&
                Objects.equal(getHeaders(), answer.getHeaders());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCode(), getBody(), getHeaders());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("body", body)
                .add("headers", headers)
                .toString();
    }

    public static Answer ok(Object body) {
        return new Answer(200, body);
    }
}