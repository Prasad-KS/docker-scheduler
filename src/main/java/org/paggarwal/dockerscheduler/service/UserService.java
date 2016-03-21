package org.paggarwal.dockerscheduler.service;

import org.paggarwal.dockerscheduler.models.User;

import java.util.Optional;

/**
 * Created by paggarwal on 3/21/16.
 */
public interface UserService {
    int create(User user);
    Optional<User> findByGithubId(int id);
}
