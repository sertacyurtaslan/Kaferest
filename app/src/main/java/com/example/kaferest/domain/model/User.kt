package com.example.kaferest.domain.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class User(
    val userId: String? = null,
    val userName: String? = null,
    val userEmail: String? = null,
    val userCoin: Double? = 0.0,
    val userCreationDate: String? = null
): Parcelable
