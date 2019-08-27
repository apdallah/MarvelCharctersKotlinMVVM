package com.apdallahy3.marvelcharcters.Network.Models

import com.squareup.moshi.Json

 data class CharacterProperty(
    val name: String,
    @Json(name = "thumbnail")
    val thumbnail: ThumbinalProperty,
    val description: String,
    val id: Int,
    @Json(name="comics")
    val comics:List<SectionProperty>,
    @Json(name="series")
    val series:List<SectionProperty>,
    @Json(name="stories")
    val stories:List<SectionProperty>,
    @Json(name="events")
    val events:List<SectionProperty>,
    @Json(name="urls")
    val urls:List<ItemUrlsProperty>

)
