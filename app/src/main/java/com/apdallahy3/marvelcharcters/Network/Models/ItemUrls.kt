package com.apdallahy3.marvelcharcters.Network.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemUrls (
    val type:String,
    val url:String

): Parcelable {
}