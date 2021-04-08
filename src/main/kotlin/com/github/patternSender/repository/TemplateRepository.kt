package com.github.patternSender.repository

import com.github.patternSender.model.Template
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface TemplateRepository : CoroutineSortingRepository<Template, Long> {
    suspend fun findByTemplateId(templateId: String): Template?
    suspend fun existsByTemplateId(templateId: String): Boolean
}