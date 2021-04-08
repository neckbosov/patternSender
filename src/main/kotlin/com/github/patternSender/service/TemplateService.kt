package com.github.patternSender.service

import com.github.patternSender.dto.MessageRecipientRequest
import com.github.patternSender.dto.MessageRequest
import com.github.patternSender.dto.TemplateRequest
import com.github.patternSender.exception.TemplateAlreadyExistsException
import com.github.patternSender.exception.TemplateNotFoundException
import com.github.patternSender.exception.VariableNotFoundException
import com.github.patternSender.model.Recipient
import com.github.patternSender.model.Template
import com.github.patternSender.model.TemplateRecipient
import com.github.patternSender.repository.RecipientRepository
import com.github.patternSender.repository.TemplateRecipientRepository
import com.github.patternSender.repository.TemplateRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

@FlowPreview
@Service
class TemplateService(
    private val templateRepository: TemplateRepository,
    private val recipientRepository: RecipientRepository,
    private val templateRecipientRepository: TemplateRecipientRepository,
    private val webClient: WebClient
) {
    suspend fun addTemplate(request: TemplateRequest) {
        val templateEntity = Template(templateId = request.templateId, templateString = request.template)
        if (templateRepository.existsByTemplateId(templateEntity.templateId)) {
            throw TemplateAlreadyExistsException("Template ${templateEntity.templateId} already exists")
        } else {
            val templateId = templateRepository.save(templateEntity).id
            request.recipients.map {
                recipientRepository.save(Recipient(url = it)).id
            }.forEach {
                val templateRecipient = TemplateRecipient(templateId = templateId, recipientId = it)
                templateRecipientRepository.save(templateRecipient)
            }

        }
    }

    suspend fun sendMessage(request: MessageRequest): List<String> {
        val templateEntity =
            templateRepository.findByTemplateId(request.templateId) ?: throw TemplateNotFoundException()
        val message = fillTemplate(templateEntity.templateString, request.variables[0])
        return templateRecipientRepository.findAllByTemplateId(templateEntity.id).map {
            recipientRepository.findById(it.recipientId)!!
        }.map { recipient ->
            webClient
                .post()
                .uri(recipient.url)
                .bodyValue(MessageRecipientRequest(message))
                .awaitExchange {
                    it.awaitBody<String>()
                }
        }.toList()
    }

    private fun fillTemplate(template: String, variables: Map<String, String>): String = buildString {
        var isVar = false
        var isEscape = false
        var varNameBuilder = StringBuilder()
        for (c in template) {
            if (isEscape) {
                if (isVar) {
                    varNameBuilder.append(c)
                } else {
                    append(c)
                }
                isEscape = false
            } else {
                if (c == '\\') {
                    isEscape = true
                } else if (isVar) {
                    if (c == '$') {
                        val varName = varNameBuilder.toString()
                        val varValue = variables[varName] ?: throw VariableNotFoundException(varName)
                        append(varValue)
                        isVar = false
                        varNameBuilder = StringBuilder()
                    } else {
                        varNameBuilder.append(c)
                    }
                } else {
                    if (c == '$') {
                        isVar = true
                    } else {
                        append(c)
                    }
                }
            }
        }
    }
}