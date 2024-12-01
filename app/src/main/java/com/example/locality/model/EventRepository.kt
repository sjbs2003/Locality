package com.example.locality.model

interface Repository{

    suspend fun searchEvents(
        token: String,
        location: String? = null,
        page: Int = 1,
        startDate: String? = null,
        endDate: String? = null,
        categories: String? = null
    ): EventResponse

    suspend fun getEventDetails(
        token: String,
        eventId: String
    ): EventDataClass

    suspend fun getVenueEvents(
        token: String,
        venueId: String,
        page: Int = 1
    ): EventResponse

    suspend fun getOrganizerEvents(
        token: String,
        organizerId: String,
        page: Int = 1
    ): EventResponse

    suspend fun searchEventsByKeyword(
        token: String,
        keyword: String,
        location: String? = null,
        page: Int = 1
    ): EventResponse
}

class EventRepository(
    private val api: ApiService
) : Repository
{
    override suspend fun searchEvents(
        token: String,
        location: String?,
        page: Int,
        startDate: String?,
        endDate: String?,
        categories: String?
    ): EventResponse = api.searchEvents(
        token = token,
        location = location,
        page = page,
        startDate = startDate,
        endDate = endDate,
        categories = categories
    )

    override suspend fun getEventDetails(
        token: String,
        eventId: String
    ): EventDataClass = api.getEventDetails(
        token = token,
        eventId = eventId
    )

    override suspend fun getVenueEvents(
        token: String,
        venueId: String,
        page: Int
    ): EventResponse = api.getVenueEvents(
        token = token,
        venueId = venueId,
        page = page
    )

    override suspend fun getOrganizerEvents(
        token: String,
        organizerId: String,
        page: Int
    ): EventResponse = api.getOrganizerEvents(
        token = token,
        organizerId = organizerId,
        page = page
    )

    override suspend fun searchEventsByKeyword(
        token: String,
        keyword: String,
        location: String?,
        page: Int
    ): EventResponse = api.searchEventsByKeyword(
        token = token,
        keyword = keyword,
        location = location,
        page = page
    )

    companion object{
        private const val DEFAULT_PAGE_SIZE = 50
    }
}