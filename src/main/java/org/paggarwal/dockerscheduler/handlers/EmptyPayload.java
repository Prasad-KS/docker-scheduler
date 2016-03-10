package org.paggarwal.dockerscheduler.handlers;

import org.paggarwal.dockerscheduler.Validable;

/**
 * Created by paggarwal on 2/22/16.
 */
public class EmptyPayload implements Validable {
    @Override
    public boolean isValid() {
        return true;
    }
}