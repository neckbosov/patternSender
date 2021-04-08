package com.github.patternSender.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("RECIPIENTS")
data class Recipient(
    @Id
    var id: Long = 0,
    var url: String = ""
)
