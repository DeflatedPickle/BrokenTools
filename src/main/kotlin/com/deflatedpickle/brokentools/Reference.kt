package com.deflatedpickle.brokentools

object Reference {
    const val MOD_ID = "brokentools"
    const val NAME = "BrokenTools"
    // Versions follow this format: MCVERSION-MAJORMOD.MAJORAPI.MINOR.PATCH.
    const val VERSION = "1.12.2-0.0.0.0"
    const val ACCEPTED_VERSIONS = "[1.12.1, 1.12.2]"

    const val CLIENT_PROXY_CLASS = "com.deflatedpickle.brokentools.client.Proxy"
    const val SERVER_PROXY_CLASS = "com.deflatedpickle.brokentools.server.Proxy"

    const val DEPENDENCIES = "required-after:forgelin"
    const val ADAPTER = "net.shadowfacts.forgelin.KotlinAdapter"
}