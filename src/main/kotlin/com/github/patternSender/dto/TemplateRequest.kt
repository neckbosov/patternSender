package com.github.patternSender.dto

data class TemplateRequest(
    val templateId: String,
    val template: String,
    val recipients: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TemplateRequest

        if (templateId != other.templateId) return false

        return true
    }

    override fun hashCode(): Int {
        return templateId.hashCode()
    }
}
