package org.paggarwal.dockerscheduler.service.impl;

import org.jooq.DSLContext;
import org.paggarwal.dockerscheduler.db.tables.records.UsersRecord;
import org.paggarwal.dockerscheduler.models.User;
import org.paggarwal.dockerscheduler.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

import static org.paggarwal.dockerscheduler.db.tables.Users.USERS;
import static org.paggarwal.dockerscheduler.models.User.Builder.anUser;

/**
 * Created by paggarwal on 3/21/16.
 */
@Service
public class UserServiceImpl implements UserService {

    @Inject
    private DSLContext dsl;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int create(User user) {
        return dsl.insertInto(USERS).columns(USERS.GITHUB_ID,USERS.NAME).values(user.getGithubId(),user.getName()).returning(USERS.ID).fetchOne().value1();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByGithubId(int id) {
        return Optional.ofNullable(dsl.selectFrom(USERS).where(USERS.GITHUB_ID.equal(id)).fetchOne(this::map));
    }

    private User map(UsersRecord record) {
        return anUser().withId(record.getId()).withGithubId(record.getGithubId()).withName(record.getName()).build();
    }
}
