package com.vincenzolisi.sneakverseauth.Repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vincenzolisi.sneakverseauth.Models.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Repository
public class SessionRepository {

    private final StringRedisTemplate redis;
    private final ObjectMapper objectMapper;

    @Value("${session.ttl-seconds}")
    private long ttlSeconds;

    @Value("${session.key-prefix}")
    private String keyPrefix;

    public SessionRepository(StringRedisTemplate redis) {
        this.redis = redis;
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void save(UserSession session) {
        String key = buildKey(session.getToken());
        String json = serialize(session);
        redis.opsForValue().set(key, json, Duration.ofSeconds(ttlSeconds));
        log.debug("Sessione salvata -> {}", key);
    }

    public Optional<UserSession> findByToken(String token) {
        String key = buildKey(token);
        String json = redis.opsForValue().get(key);
        if(json == null) {
            log.debug("Sessione non trovata o scaduta -> {}", key);
            return Optional.empty();
        }
        return Optional.of(deserialize(json));
    }

    public void delete(String token) {
        String key = buildKey(token);
        redis.delete(key);
        log.debug("Sessione eliminata -> {}", key);
    }

    public void refresh(String token) {
        String key = buildKey(token);
        redis.expire(key, Duration.ofSeconds(ttlSeconds));
        log.debug("TTL sessione resettato -> {}", key);
    }

    public long getTtl(String token) {
        Long ttl = redis.getExpire(key(token));
        return ttl != null ? ttl: -2;
    }

    private String buildKey(String token) {
        return keyPrefix + token;
    }

    private String key(String token) {
        return buildKey(token);
    }

    private String serialize(UserSession session) {
        try {
            return objectMapper.writeValueAsString(session);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Errore serializzazione sessione", e);
        }
    }

    private UserSession deserialize(String json) {
        try {
            return objectMapper.readValue(json, UserSession.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Errore deserializzazione sessione", e);
        }
    }
}
