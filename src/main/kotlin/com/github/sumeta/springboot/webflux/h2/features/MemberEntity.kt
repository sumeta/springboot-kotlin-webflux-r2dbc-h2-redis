package com.github.sumeta.springboot.webflux.h2.features

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(value = "MEMBER")
class MemberEntity(
    @Id
    val id: String,
    val firstName: String,
    val lastName: String,
    val createdBy: String,
    val createdDate: LocalDateTime?
)
