package com.github.patternSender.controller

import com.github.patternSender.dto.MessageRequest
import com.github.patternSender.dto.SendResponse
import com.github.patternSender.dto.TemplateRequest
import com.github.patternSender.service.TemplateService
import kotlinx.coroutines.FlowPreview
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@FlowPreview
@RestController
class TemplateController(private val templateService: TemplateService) {
    @PostMapping("/template")
    suspend fun addTemplate(@RequestBody request: TemplateRequest) {
        templateService.addTemplate(request)
    }

    @PostMapping("/send")
    suspend fun sendMessage(@RequestBody request: MessageRequest): SendResponse {
        return SendResponse(templateService.sendMessage(request))
    }
}