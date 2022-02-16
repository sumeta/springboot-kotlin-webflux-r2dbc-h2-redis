package com.github.sumeta.springboot.webflux.h2.features

import com.github.sumeta.springboot.webflux.h2.features.dto.MemberResponse
import com.github.sumeta.springboot.webflux.h2.helper.buildReactiveRedisTemplate
import com.github.sumeta.springboot.webflux.h2.helper.putData
import com.github.sumeta.springboot.webflux.h2.helper.removeByKey
import kotlinx.coroutines.reactive.asFlow
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitFirstOrNull
import org.springframework.data.r2dbc.core.awaitOneOrNull
import org.springframework.data.r2dbc.core.awaitRowsUpdated
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.getAndAwait
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Repository

@Repository
class MemberRepository(
        private val redisFactory: ReactiveRedisConnectionFactory,
        private val databaseClient: DatabaseClient) {

    suspend fun get(id:String) =
            databaseClient.select().from(MemberEntity::class.java)
                    .matching(Criteria.where(MemberEntity::id.name) .`is`(id))
                    .fetch().awaitFirstOrNull()

    suspend fun add(memberEntity: MemberEntity) =
            databaseClient.insert()
                    .into(MemberEntity::class.java)
                    .using(memberEntity)
                    .fetch()
                    .awaitOneOrNull()

    suspend fun edit(memberEntity: MemberEntity) =
            databaseClient.update()
                    .table(MemberEntity::class.java)
                    .using(memberEntity)
                    .fetch()
                    .awaitRowsUpdated()

    suspend fun delete(id:String) =
            databaseClient.delete()
                    .from(MemberEntity::class.java)
                    .matching(Criteria.where(MemberEntity::id.name).`is`(id))
                    .fetch()
                    .awaitRowsUpdated()

    // Redis
    suspend fun getRedisKey(key: String) =
        memberRedisTemplate.opsForValue().getAndAwait("Member::$key")

    suspend fun setRedis(memberEntity: MemberEntity) =
        memberRedisTemplate.putData("Member::"+memberEntity.id, memberEntity)

    suspend fun removeRedis(id: String) =
        memberRedisTemplate.removeByKey("Member::$id")

    private val memberRedisTemplate =
        redisFactory.buildReactiveRedisTemplate<MemberEntity>()


}