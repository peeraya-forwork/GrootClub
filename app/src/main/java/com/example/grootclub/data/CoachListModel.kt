package com.example.grootclub.data


class CoachListModel : ArrayList<CoachListModelItem>()
data class CoachListModelItem(
    val _id: String,
    val des: String,
    val id: Int,
    val image: String,
    val name: String,
    val slots_today: List<SlotsToday>,
    val slots_tomr: List<SlotsTomr>,
    val type: String
)

data class SlotsToday(
    val _id: String,
    val endTime: String,
    val isBooked: Boolean,
    val startTime: String
)

data class SlotsTomr(
    val _id: String,
    val endTime: String,
    val isBooked: Boolean,
    val startTime: String
)