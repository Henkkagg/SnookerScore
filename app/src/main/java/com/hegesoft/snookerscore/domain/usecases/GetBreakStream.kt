package com.hegesoft.snookerscore.domain.usecases

import com.hegesoft.snookerscore.domain.BreakRepository
import com.hegesoft.snookerscore.domain.models.BreakUi
import com.hegesoft.snookerscore.domain.models.toBreakUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBreakStream @Inject constructor(private val repository: BreakRepository) {

    operator fun invoke() = repository.getNewest().flowOn(Dispatchers.IO).map { it.toBreakUi() }
}