package com.github.patternSender.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class VariableNotFoundException(varName: String) :
    ResponseStatusException(HttpStatus.BAD_REQUEST, "Variable $varName is not found")