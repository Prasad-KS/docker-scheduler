package org.paggarwal.dockerscheduler.jobs;

import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.StreamType;
import com.github.dockerjava.core.command.LogContainerResultCallback;

/**
 * Created by paggarwal on 3/14/16.
 */

public class LogHandler extends LogContainerResultCallback {
    private StringBuilder stdOut = new StringBuilder();
    private StringBuilder stdErr = new StringBuilder();

    @Override
    public void onNext(Frame item) {
        switch (item.getStreamType()) {
            case STDOUT:
                stdOut.append(new String(item.getPayload()));
                break;
            case STDERR:
                stdErr.append(new String(item.getPayload()));
                break;
            default:
                // Do nothing
        }
    }

    public String getStdErr() {
        return stdErr.toString();
    }

    public String getStdOut() {
        return stdOut.toString();
    }
}
