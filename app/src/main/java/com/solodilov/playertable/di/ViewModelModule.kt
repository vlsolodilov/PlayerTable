package com.solodilov.playertable.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solodilov.playertable.presentation.common.ViewModelFactory
import com.solodilov.playertable.presentation.playertable.TableViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TableViewModel::class)
    fun bindTableViewModel(viewModel: TableViewModel): ViewModel

}