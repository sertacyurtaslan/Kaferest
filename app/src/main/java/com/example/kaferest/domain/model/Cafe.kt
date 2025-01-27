package com.example.kaferest.domain.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class Cafe (
    val name: String? = null,
    val image: String? = null,
    val rating: Double = 0.0,
    val distance: String? = null,
    val isPromoted: Boolean = false
) : Parcelable

