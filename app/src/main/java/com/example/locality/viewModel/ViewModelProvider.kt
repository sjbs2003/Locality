package com.example.locality.viewModel

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.locality.LocalityApplication

object ViewModelProvider {

    val Factory = viewModelFactory {

        initializer {
            val application = (this[APPLICATION_KEY] as LocalityApplication)
            val eventRepository = application.container.repository
            HomeViewModel(eventRepository = eventRepository)
        }
    }
}