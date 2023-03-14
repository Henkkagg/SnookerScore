package com.hegesoftware.snookerscore.domain.usecases

import com.hegesoftware.snookerscore.domain.BreakRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewGame @Inject constructor(private val repository: BreakRepository) {

    suspend operator fun invoke() = withContext(Dispatchers.IO) {

        repository.deleteAll()
        repository.addNew()
    }
}