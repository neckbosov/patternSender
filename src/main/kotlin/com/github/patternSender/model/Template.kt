package com.github.patternSender.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("TEMPLATES")
data class Template(
    @Id
    var id: Long = 0,
    @Column("template_id")
    var templateId: String = "",
    @Column("template_string")
    var templateString: String = ""
)