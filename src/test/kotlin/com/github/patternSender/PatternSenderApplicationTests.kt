package com.github.patternSender

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.patternSender.dto.HttpbinResponse
import com.github.patternSender.dto.MessageRequest
import com.github.patternSender.dto.SendResponse
import com.github.patternSender.dto.TemplateRequest
import kotlinx.coroutines.FlowPreview
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient


@FlowPreview
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
class PatternSenderApplicationTests {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun testAPI() {
        val templateRequest = TemplateRequest(
            templateId = "kek",
            template = "Hello, \$value\$",
            recipients = arrayOf("https://httpbin.org/post")
        )
        webTestClient.post()
            .uri("/template")
            .bodyValue(templateRequest)
            .exchange()
            .expectStatus().isOk

        val messageRequest = MessageRequest(
            "kek",
            arrayOf(mapOf("value" to "world"))
        )
        val message = webTestClient.post()
            .uri("/send")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(messageRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody().jsonPath("$.recipientResponses.length()").isEqualTo(1)
            .returnResult().responseBody?.let {
                mapper.readValue<SendResponse>(it.decodeToString())
            }
        Assertions.assertNotNull(message)
        message!!
        val httpbinResponse = mapper.readValue<HttpbinResponse>(message.recipientResponses[0])
        Assertions.assertEquals(httpbinResponse.json["message"], "Hello, world")
    }

}
