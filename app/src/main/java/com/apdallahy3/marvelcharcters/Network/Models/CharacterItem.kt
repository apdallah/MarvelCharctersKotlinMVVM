package com.apdallahy3.marvelcharcters.Network.Models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterItem(
    val id: Int,
    val name: String,
    @Json(name = "thumbnail")
    val thumbnail: ThumbinalItem,
    val description: String,
    @Json(name="comics")
    val comics:SectionProperty,
    @Json(name="series")
    val series:SectionProperty,
    @Json(name="stories")
    val stories:SectionProperty,
    @Json(name="events")
    val events:SectionProperty,
    @Json(name="urls")
    val urls:List<ItemUrls>


): Parcelable {}
