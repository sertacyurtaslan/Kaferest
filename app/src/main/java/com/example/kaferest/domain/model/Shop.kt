package com.example.kaferest.domain.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class Shop(
    val shopId: String? = null,
    val shopName: String? = null,
    val shopMail: String? = null,
    val shopPassword: String? = null,
    val shopImages: String? = null,
    val shopAddress: String? = null,
    val shopRating: Double = 0.0,
    val shopCreationDate: String? = null
) : Parcelable

