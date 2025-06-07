package com.samardash.lamda;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/api/v1/cache")
public class RedisGetController {

    private final JedisPool jedisPool;

    public RedisGetController(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @GetMapping("/get/{key}")
    public ResponseEntity<String> getCache(@PathVariable String key) {
        try (var jedis = jedisPool.getResource()) {
            String value = jedis.get(key);
            if (value == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(value);
        }
    }
}
