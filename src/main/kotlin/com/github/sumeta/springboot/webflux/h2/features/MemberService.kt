package com.github.sumeta.springboot.webflux.h2.features

import com.github.sumeta.springboot.webflux.h2.features.dto.MemberRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MemberService(private val memberRepository: MemberRepository) {

    suspend fun get(id : String) =
        memberRepository.getRedisKey(id) ?:
        run {
            println("get db")
            memberRepository.get(id)
        }?.also {
            println("set redis")
            memberRepository.setRedis(it)
        }?:throw Exception()


    suspend fun add(request: MemberRequest) =
      memberRepository.add(
          MemberEntity(
              id = request.id,
              firstName = request.firstName,
              lastName = request.lastName,
              createdBy = "Top",
              createdDate = LocalDateTime.now()
          )
      )

    suspend fun edit(request: MemberRequest) =
        memberRepository.removeRedis(request.id).let {
            memberRepository.edit(
                MemberEntity(
                    id = request.id,
                    firstName = request.firstName,
                    lastName = request.lastName,
                    createdBy = "Sumeta",
                    createdDate = LocalDateTime.now()
                )
            )
        }

    suspend fun delete(id: String) =
        memberRepository.removeRedis(id).let {
            memberRepository.delete(id)
        }
}