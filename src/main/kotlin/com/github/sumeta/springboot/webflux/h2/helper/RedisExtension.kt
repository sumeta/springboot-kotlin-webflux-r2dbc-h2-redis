package com.github.sumeta.springboot.webflux.h2.helper

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.*
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

inline fun <reified T> ReactiveRedisConnectionFactory.buildReactiveRedisTemplate(): ReactiveRedisTemplate<String, T> =
    ReactiveRedisTemplate(
        this,
        RedisSerializationContext.newSerializationContext<String, T>(
            StringRedisSerializer()
        ).value(
            Jackson2JsonRedisSerializer(T::class.java).apply {
                this.setObjectMapper(jacksonObjectMapper().let {
                    it.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    it.registerModule(JavaTimeModule())
                })
            }
        ).build()
    )

suspend inline fun <reified V> ReactiveRedisTemplate<String, V>.removeByKey(key: String) =
    this.opsForValue().delete(key).awaitFirstOrNull()

suspend inline fun <reified V : Any> ReactiveRedisTemplate<String, V>.getData(key: String) =
    this.opsForValue().getAndAwait(key)

suspend inline fun <reified V : Any> ReactiveRedisTemplate<String, V>.getMultiData(key: String) =
    this.opsForValue().multiGetAndAwait(key)

suspend inline fun <reified V : Any> ReactiveRedisTemplate<String, V>.putDataWithDuration(
    key: String,
    data: V,
    duration: Duration?
) = this.opsForValue().setAndAwait(key, data, duration ?: Duration.ofMinutes(1))

suspend inline fun <reified V : Any> ReactiveRedisTemplate<String, V>.putData(
    key: String,
    data: V
) = this.opsForValue().setAndAwait(key, data)

suspend inline fun <reified V : Any> ReactiveRedisTemplate<String, V>.putMultiData(mapMultiData: Map<String, V>) =
    this.opsForValue().multiSetAndAwait(mapMultiData)


