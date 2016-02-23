package org.paggarwal.rancherscheduler.handlers;

import org.paggarwal.rancherscheduler.Validable;

/**
 * Created by paggarwal on 2/22/16.
 */
public class EmptyPayload implements Validable {
    @Override
    public boolean isValid() {
        return true;
    }
}