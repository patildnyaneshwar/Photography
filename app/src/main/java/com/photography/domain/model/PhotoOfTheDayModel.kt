package com.photography.domain.model

import com.photography.ui.presentation.home.model.Links
import com.photography.ui.presentation.home.model.Urls

data class PhotoOfTheDayModel(
    val id: String,
    val slug: String,
    val color: String?,
    val description: String?,
    val urls: Urls?,
    val links: Links?
)
