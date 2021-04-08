package com.github.patternSender.repository

import com.github.patternSender.model.Recipient
import org.springframework.data.repository.kotlin.CoroutineSortingRepository

interface RecipientRepository : CoroutineSortingRepository<Recipient, Long>