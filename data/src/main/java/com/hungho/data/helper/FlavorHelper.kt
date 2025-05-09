package com.hungho.data.helper

import com.hungho.data.BuildConfig

internal object FlavorHelper {
    fun isDevMode() = BuildConfig.FLAVOR == "dev"

    fun isProdMode() = BuildConfig.FLAVOR == "prod"
}