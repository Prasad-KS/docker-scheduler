package org.paggarwal.dockerscheduler.service.impl;

import com.auth0.jwt.JWTSigner;
import com.google.common.collect.ImmutableMap;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.paggarwal.dockerscheduler.service.SettingsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableMap.of;
import static org.paggarwal.dockerscheduler.db.tables.Settings.SETTINGS;

/**
 * Created by paggarwal on 3/22/16.
 */
@Service
public class SettingsServiceImpl implements SettingsService {
    @Inject
    private JWTSigner jwtSigner;

    @Inject
    private DSLContext dsl;

    @Transactional(readOnly = true)
    @Override
    public Map<String, String> list() {
        return dsl.select(SETTINGS.NAME,SETTINGS.VALUE).from(SETTINGS).fetch().stream().collect(Collectors.toMap(Record2::value1,Record2::value2));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean generateAPIKey() {
        String newToken = jwtSigner.sign(of("sub", "system", "iat", System.currentTimeMillis(), "exp", System.currentTimeMillis() + 365 * 10 * 24 * 60 * 60 * 1000));
        return dsl.insertInto(SETTINGS,SETTINGS.NAME,SETTINGS.VALUE).values("apiKey", newToken).onDuplicateKeyUpdate().set(SETTINGS.VALUE,newToken).execute() > 0;
    }
}
