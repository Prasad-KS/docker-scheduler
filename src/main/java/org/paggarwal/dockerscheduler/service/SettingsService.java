package org.paggarwal.dockerscheduler.service;

import org.paggarwal.dockerscheduler.models.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by paggarwal on 3/14/16.
 */
public interface SettingsService {
    Map<String,String> list();

    boolean generateAPIKey();
}
