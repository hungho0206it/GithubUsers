package com.hungho.data.local.storage.helper

import com.hungho.data.BuildConfig

object FlavorHelper {
    fun isDevMode() = BuildConfig.FLAVOR == "dev"

    fun isProdMode() = BuildConfig.FLAVOR == "prod"
}