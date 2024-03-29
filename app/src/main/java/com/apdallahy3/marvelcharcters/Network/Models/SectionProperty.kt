package com.apdallahy3.marvelcharcters.Network.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SectionProperty(
    val available:Int,
    val collectionURI:String,
    val items:List<SectionItem>
) : Parcelable {
}