package com.google

import com.google.android.gms.auth.api.signin.GoogleSignInOptions

object GoogleConfiguration {
    private lateinit var clientId: String

    internal var gso: GoogleSignInOptions? = null
        private set

    @JvmStatic
    fun clientId(clientId: String): GoogleConfiguration {
        GoogleConfiguration.clientId = clientId
        return this
    }

    fun build() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientId)
                .requestEmail()
                .build()
    }
}