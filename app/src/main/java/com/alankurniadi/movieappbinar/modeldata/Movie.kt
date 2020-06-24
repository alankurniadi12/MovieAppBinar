package com.alankurniadi.movieappbinar.modeldata

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie (
    var id: Int = 0,
    var poster_path: String? = null,
    var backround_path: String? = null,
    var title: String? = null,
    var vote_average: Int = 0,
    var overview: String? = null,
    var release_date: String? = null
): Parcelable