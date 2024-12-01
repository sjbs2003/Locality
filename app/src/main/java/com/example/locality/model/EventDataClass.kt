package com.example.locality.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class EventResponse(
    val events: List<EventDataClass>,
    val pagination: Pagination
)

@Serializable
data class EventDataClass(
    val id: String,
    val name: String,
    val description: String,
    @SerialName("start")
    val startDate: String,
    @SerialName("end")
    val endDate: String,
    val imageUrl: String?,
    val venueName: String,
    val location: String
)

@Serializable
data class Pagination(
    @SerialName("object_count")
    val objectCount: Int,
    @SerialName("page_count")
    val pageCount: Int,
    @SerialName("page_number")
    val pageNumber: Int,
    @SerialName("page_size")
    val pageSize: Int,
    @SerialName("has_more_items")
    val hasMoreItems: Boolean
)