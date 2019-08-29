package com.apdallahy3.marvelcharcters.Network.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SectionItem(
    val resourceURI:String,
    val name:String
): Parcelable {
}