package com.photography.utils

data class UiState<T>(val success: T, val loading: Boolean = false, val error: Throwable)
