package com.solodilov.playertable.di

import android.app.Application
import com.solodilov.playertable.presentation.playertable.TableFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
	DataModule::class,
	ViewModelModule::class,
])
interface ApplicationComponent {

	fun inject(fragment: TableFragment)

	@Component.Factory
	interface Factory {
		fun create(
			@BindsInstance application: Application,
		): ApplicationComponent
	}
}