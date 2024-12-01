package com.example.locality.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.locality.model.EventDataClass
import com.example.locality.viewModel.EventUiState
import com.example.locality.viewModel.HomeViewModel
import com.example.locality.viewModel.ViewModelProvider

@Composable
fun EventScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
   Column(modifier = modifier.fillMaxSize()) {

       // SearchBar
       SearchBar(
           query = viewModel.searchQuery,
           onQueryChange = { viewModel.searchEvents(it) },
           modifier = modifier.padding(16.dp)
       )

       // Location Filter
       LocationFilter(
           selectedLocation = viewModel.selectedLocation,
           onLocationChanged = { viewModel.updateLocation(it) },
           modifier = modifier.padding(horizontal = 16.dp)
       )

       // Content
       when (val uiState = viewModel.eventUiState) {
           is EventUiState.Loading -> LoadingScreen()
           is EventUiState.Error -> ErrorScreen()
           is EventUiState.Success -> EventList(
               events = uiState.events,
               hasMoreEvents = uiState.hasMoreEvents,
               onLoadMore = { viewModel.loadNextPage() }
           )
       }
   }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search events...") },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun LocationFilter(
    selectedLocation: String?,
    onLocationChanged: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = selectedLocation ?: "",
        onValueChange = { onLocationChanged(it.takeIf { it.isNotBlank() }) },
        placeholder = { Text("Enter Location...") },
        modifier = modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun EventList(
    events: List<EventDataClass>,
    hasMoreEvents: Boolean,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(events){ event ->
            EventCard(event = event)
        }

        if (hasMoreEvents) {
            item { LoadMoreButton(onClick = onLoadMore) }
        }
    }
}

@Composable
fun EventCard(
    event: EventDataClass,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = event.name,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = event.venueName,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Date: ${event.venueName}",
                style = MaterialTheme.typography.bodyMedium
            )

            if (event.description.isNotBlank()){
                Spacer(modifier.height(8.dp))
                Text(
                    text = event.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun LoadMoreButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Text("LoadMore...")
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Something went wrong!",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Please try again later",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}