package org.paggarwal.dockerscheduler;

import java.util.Map;

/**
 * Created by paggarwal on 2/22/16.
 */
public interface RequestHandler<V extends Validable> {
    Answer process(V value, Map<String, String> urlParams);
}