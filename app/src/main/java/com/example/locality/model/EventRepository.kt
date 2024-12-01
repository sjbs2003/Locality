package com.example.locality.model

interface Repository{

    suspend fun searchEvents(
        location: String? = null,
        page: Int = 1,
        startDate: String? = null,
        endDate: String? = null,
        categories: String? = null
    ): EventResponse

    suspend fun getEventDetails(
        eventId: String
    ): EventDataClass

    suspend fun getVenueEvents(
        venueId: String,
        page: Int = 1
    ): EventResponse

    suspend fun getOrganizerEvents(
        organizerId: String,
        page: Int = 1
    ): EventResponse

    suspend fun searchEventsByKeyword(
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
        location: String?,
        page: Int,
        startDate: String?,
        endDate: String?,
        categories: String?
    ): EventResponse = api.searchEvents(
        location = location,
        page = page,
        startDate = startDate,
        endDate = endDate,
        categories = categories
    )

    override suspend fun getEventDetails(
        eventId: String
    ): EventDataClass = api.getEventDetails(
        eventId = eventId
    )

    override suspend fun getVenueEvents(
        venueId: String,
        page: Int
    ): EventResponse = api.getVenueEvents(
        venueId = venueId,
        page = page
    )

    override suspend fun getOrganizerEvents(
        organizerId: String,
        page: Int
    ): EventResponse = api.getOrganizerEvents(
        organizerId = organizerId,
        page = page
    )

    override suspend fun searchEventsByKeyword(
        keyword: String,
        location: String?,
        page: Int
    ): EventResponse = api.searchEventsByKeyword(
        keyword = keyword,
        location = location,
        page = page
    )

    companion object{
        private const val DEFAULT_PAGE_SIZE = 50
    }
}