package com.apdallahy3.marvelcharcters.Network.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ThumbinalItem(val path: String, val extension: String) : Parcelable {}