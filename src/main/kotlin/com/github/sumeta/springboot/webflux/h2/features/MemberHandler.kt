package com.github.sumeta.springboot.webflux.h2.features

import com.github.sumeta.springboot.webflux.h2.features.dto.MemberRequest
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import java.time.LocalDateTime

@Component
class MemberHandler(
    private val memberService: MemberService
    ) {

    suspend fun get(serverRequest: ServerRequest):ServerResponse =
        memberService.get(serverRequest.pathVariable("id")).let {
            ServerResponse.ok().json().bodyValueAndAwait(it)
        }

    suspend fun add(serverRequest: ServerRequest) =
        ServerResponse.ok().json().bodyValueAndAwait(
            serverRequest.awaitBody<MemberRequest>().let {
                kotlin.runCatching {
                    memberService.add(it)
                }.onSuccess {
                    return ServerResponse.ok().bodyValueAndAwait("Success")
                }.onFailure {
                    return ServerResponse.badRequest().buildAndAwait()
                }
            }
        )

    suspend fun edit(serverRequest: ServerRequest) =
            ServerResponse.ok().json().bodyValueAndAwait(
                    serverRequest.awaitBody<MemberRequest>().let {
                        kotlin.runCatching {
                            memberService.edit(it)
                        }.onSuccess {
                            return ServerResponse.ok().bodyValueAndAwait("Success")
                        }.onFailure {
                            return ServerResponse.badRequest().buildAndAwait()
                        }
                    }
            )

    suspend fun delete(serverRequest: ServerRequest):ServerResponse{
        return ServerResponse.ok().json().bodyValueAndAwait(
            memberService.delete(serverRequest.pathVariable("id"))
        )
    }

}