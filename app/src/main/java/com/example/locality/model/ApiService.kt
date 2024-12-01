package com.example.locality.model

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("event/search")
    suspend fun searchEvents(
        @Query("token") token: String,
        @Query("location.address") location: String? = null,
        @Query("page") page: Int = 1,
        @Query("start_date.range_start") startDate: String? = null,
        @Query("start_date.range_end") endDate: String? = null,
        @Query("categories") categories: String? = null
    ): EventResponse

    @GET("events/{event_id}")
    suspend fun getEventDetails(
        @Query("token") token: String,
        @Path("event_id") eventId: String
    ): EventDataClass

    @GET("venues/{venue_id}/events")
    suspend fun getVenueEvents(
        @Query("token") token: String,
        @Path("venue_id") venueId: String,
        @Query("page") page: Int = 1
    ): EventResponse

    @GET("organizers/{organizer_id}/events")
    suspend fun getOrganizerEvents(
        @Query("token") token: String,
        @Path("organizer_id") organizerId: String,
        @Query("page") page: Int = 1
    ): EventResponse

    @GET("events/search")
    suspend fun searchEventsByKeyword(
        @Query("token") token: String,
        @Query("q") keyword: String,
        @Query("location.address") location: String? = null,
        @Query("page") page: Int = 1
    ): EventResponse

}
