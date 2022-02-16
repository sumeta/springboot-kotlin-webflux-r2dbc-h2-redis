package com.github.sumeta.springboot.webflux.h2.features.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
class MemberResponse (
    val id: String,
    val firstName: String,
    val lastName: String,
    val createdBy: String
//    val createdDate: LocalDateTime
)
