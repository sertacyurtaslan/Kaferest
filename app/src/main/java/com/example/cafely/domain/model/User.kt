package com.example.cafely.domain.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
@Immutable
data class User(
    val userId: String? = null,
    val userName: String? = null,
    val userEmail: String? = null,
    val userBalance: String? = null,
    val userCurrency: String? = null,
    val userCreationDate: String? = null,
    val userProfilePhoto: String? = null

): Parcelable
