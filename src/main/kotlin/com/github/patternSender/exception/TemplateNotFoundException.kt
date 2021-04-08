package com.github.patternSender.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class TemplateNotFoundException : ResponseStatusException(HttpStatus.NOT_FOUND, "Template not found")