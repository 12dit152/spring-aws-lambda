package com.samardash.lamda;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/api/v1/cache")
public class RedisSetController {

    private final JedisPool jedisPool;

    public RedisSetController(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @GetMapping("/set/{key}/{value}")
    public ResponseEntity<String> setCache(@PathVariable String key, @PathVariable String value) {
        try (var jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        }
        return ResponseEntity.ok("Value set in Redis for key: " + key);
    }
}
