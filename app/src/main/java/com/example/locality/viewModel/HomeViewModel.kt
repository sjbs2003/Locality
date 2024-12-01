package com.example.locality.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locality.model.EventDataClass
import com.example.locality.model.EventRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface EventUiState {
    data class Success(
        val events: List<EventDataClass>,
        val currentPage: Int,
        val hasMoreEvents: Boolean
    ) : EventUiState

    object Error : EventUiState
    object Loading : EventUiState
}

class HomeViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {

    var eventUiState: EventUiState by mutableStateOf(EventUiState.Loading)
        private set

    var searchQuery: String by mutableStateOf("")
        private set

    var selectedLocation: String? by mutableStateOf(null)
        private set

    init {
        getEvents()
    }

    fun getEvents(page: Int = 1) {
        viewModelScope.launch {
            eventUiState = EventUiState.Loading
            eventUiState = try {
                val result = eventRepository.searchEvents(
                    location = selectedLocation,
                    page = page
                )
                EventUiState.Success(
                    events = result.events,
                    currentPage = result.pagination.pageNumber,
                    hasMoreEvents = result.pagination.hasMoreItems
                )
            } catch (e: IOException) {
                EventUiState.Error
            } catch (e: Exception) {
                EventUiState.Error
            }
        }
    }

    fun searchEvents(keyword: String) {
        viewModelScope.launch {
            searchQuery = keyword
            eventUiState = EventUiState.Loading
            eventUiState = try {
                val result = eventRepository.searchEventsByKeyword(
                    keyword = keyword,
                    location = selectedLocation
                )
                EventUiState.Success(
                    events = result.events,
                    currentPage = result.pagination.pageNumber,
                    hasMoreEvents = result.pagination.hasMoreItems
                )
            } catch (e: IOException) {
                EventUiState.Error
            } catch (e: Exception) {
                EventUiState.Error
            }
        }
    }

    fun updateLocation(location: String?) {
        selectedLocation = location
        // Refresh events with new location
        getEvents()
    }

    fun loadNextPage() {
        val currentState = eventUiState
        if (currentState is EventUiState.Success && currentState.hasMoreEvents) {
            getEvents(currentState.currentPage + 1)
        }
    }
}