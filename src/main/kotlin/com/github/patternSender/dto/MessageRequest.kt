package com.github.patternSender.dto

data class MessageRequest(val templateId: String, val variables: Array<Map<String, String>>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MessageRequest

        if (templateId != other.templateId) return false
        if (!variables.contentEquals(other.variables)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = templateId.hashCode()
        result = 31 * result + variables.contentHashCode()
        return result
    }
}