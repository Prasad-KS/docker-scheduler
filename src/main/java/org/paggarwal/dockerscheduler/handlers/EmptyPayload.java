package org.paggarwal.dockerscheduler.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import org.paggarwal.dockerscheduler.Validable;

/**
 * Created by paggarwal on 2/22/16.
 */
public class EmptyPayload implements Validable {
    public static final TypeReference<EmptyPayload> TYPE_REFERENCE = new TypeReference<EmptyPayload>() {};

    @Override
    public boolean isValid() {
        return true;
    }
}