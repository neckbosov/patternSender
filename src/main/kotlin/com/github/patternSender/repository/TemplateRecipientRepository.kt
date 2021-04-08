package com.github.patternSender.repository

import com.github.patternSender.model.TemplateRecipient
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineSortingRepository

interface TemplateRecipientRepository : CoroutineSortingRepository<TemplateRecipient, Long> {
    @FlowPreview
    fun findAllByTemplateId(templateId: Long): Flow<TemplateRecipient>
}