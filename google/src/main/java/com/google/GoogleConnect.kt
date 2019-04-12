package com.google

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo

object GoogleConnect {
    internal var account: GoogleSignInAccount? = null
        private set

    @JvmStatic
    internal val auth = FirebaseAuth.getInstance()

    @JvmStatic
    fun with(): Builder {
        return Builder()
    }

    @JvmStatic
    fun onActivityResult(data: Intent, success: Boolean.() -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            account = task.getResult(ApiException::class.java)
            success(true)
        } catch (e: ApiException) {
            Log.w("Login", "Google sign in failed", e)
            account = null
            success(false)
        }
    }

    @JvmStatic
    val user: UserInfo?
        get() {
            FirebaseAuth.getInstance().currentUser?.let {
                it.providerData.forEach {
                    if (it.providerId == "google.com") {
                        return it
                    }
                }
            }
            return null
        }

}