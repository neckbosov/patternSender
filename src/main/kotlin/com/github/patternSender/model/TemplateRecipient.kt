package com.github.patternSender.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("TEMPLATE_RECIPIENT")
data class TemplateRecipient(
    @Id
    var id: Long = 0,
    @Column("template_id")
    var templateId: Long = 0,
    @Column("recipient_id")
    var recipientId: Long = 0,
)
