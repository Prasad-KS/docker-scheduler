package org.paggarwal.dockerscheduler.models;

import org.paggarwal.dockerscheduler.Validable;

import java.util.ArrayList;

/**
 * Created by paggarwal on 3/11/16.
 */
public class ValidableList<T extends Validable> extends ArrayList<T> implements Validable {

    @Override
    public boolean isValid() {
        return !this.stream().filter(t -> !t.isValid()).findFirst().isPresent();
    }
}
