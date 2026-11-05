package com.eraorder.arena.core.di

import android.content.Context
import com.eraorder.arena.data.local.PreferencesStore
import com.eraorder.arena.data.repository.EventRepositoryImpl
import com.eraorder.arena.data.repository.PreferencesRepositoryImpl
import com.eraorder.arena.data.repository.ProgressRepositoryImpl
import com.eraorder.arena.domain.repository.EventRepository
import com.eraorder.arena.domain.repository.PreferencesRepository
import com.eraorder.arena.domain.repository.ProgressRepository
import com.eraorder.arena.domain.usecase.DealRoundUseCase
import com.eraorder.arena.domain.usecase.ResolveDailyEraUseCase
import com.eraorder.arena.domain.usecase.UpdateProgressUseCase
import com.eraorder.arena.domain.usecase.ValidatePlacementUseCase

object ServiceLocator {

    private var store: PreferencesStore? = null

    fun init(context: Context) {
        if (store == null) store = PreferencesStore(context)
    }

    private fun requireStore(): PreferencesStore =
        checkNotNull(store) { "ServiceLocator.init was not called" }

    val eventRepository: EventRepository by lazy { EventRepositoryImpl() }

    val progressRepository: ProgressRepository by lazy { ProgressRepositoryImpl(requireStore()) }

    val preferencesRepository: PreferencesRepository by lazy {
        PreferencesRepositoryImpl(requireStore())
    }

    val dealRound: DealRoundUseCase by lazy { DealRoundUseCase(eventRepository) }

    val validatePlacement: ValidatePlacementUseCase by lazy { ValidatePlacementUseCase() }

    val resolveDailyEra: ResolveDailyEraUseCase by lazy { ResolveDailyEraUseCase(eventRepository) }

    val updateProgress: UpdateProgressUseCase by lazy { UpdateProgressUseCase(progressRepository) }
}
