package com.hegesoft.snookerscore.domain.usecases

import com.hegesoft.snookerscore.data.BreakDb
import com.hegesoft.snookerscore.domain.BreakRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class NewBreak @Inject constructor(private val repository: BreakRepository) {

    suspend operator fun invoke() = withContext(Dispatchers.IO) {

        repository.addNew()
    }
}