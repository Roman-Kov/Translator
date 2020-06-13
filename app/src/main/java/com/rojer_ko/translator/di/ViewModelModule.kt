package com.rojer_ko.translator.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rojer_ko.translator.presentation.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [InteractorModule::class])
internal abstract class ViewModelModule{
    @Binds
    internal abstract fun bindViewModuleFactory(factory: ViewModelFactory):ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)

    protected abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel
}