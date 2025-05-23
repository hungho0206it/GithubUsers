package com.hungho.data.helper

import com.hungho.data.BuildConfig

internal interface FlavorProvider {
    fun getFlavor(): String
}

internal object BuildFlavor : FlavorProvider {
    override fun getFlavor(): String {
        return BuildConfig.FLAVOR
    }
}

internal class FlavorHelper(private val flavorProvider: FlavorProvider) {
    fun isDevMode() = flavorProvider.getFlavor() == "dev"

    fun isProdMode() = flavorProvider.getFlavor() == "prod"
}