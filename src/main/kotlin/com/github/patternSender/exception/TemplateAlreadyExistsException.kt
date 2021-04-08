package com.github.patternSender.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class TemplateAlreadyExistsException(reason: String = "This reason already exists") :
    ResponseStatusException(HttpStatus.BAD_REQUEST, reason)