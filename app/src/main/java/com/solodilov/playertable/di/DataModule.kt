package com.solodilov.playertable.di

import android.app.Application
import com.solodilov.playertable.data.datasource.GameDatasource
import com.solodilov.playertable.data.datasource.GameDatasourceImpl
import com.solodilov.playertable.data.datasource.database.PlayerTableDao
import com.solodilov.playertable.data.datasource.database.PlayerTableDatabase
import com.solodilov.playertable.data.repository.GameRepositoryImpl
import com.solodilov.playertable.domain.repository.GameRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataModule {

	@Singleton
	@Binds
	fun bindGameDatasource(impl: GameDatasourceImpl): GameDatasource

	@Singleton
	@Binds
	fun bindGameRepository(impl: GameRepositoryImpl): GameRepository

	companion object {

		@Singleton
		@Provides
		fun providePlayerTableDatabase(application: Application): PlayerTableDatabase =
			PlayerTableDatabase.getInstance(application)


		@Singleton
		@Provides
		fun providePlayerTableDao(database: PlayerTableDatabase): PlayerTableDao =
			database.playerTableDao()

	}
}