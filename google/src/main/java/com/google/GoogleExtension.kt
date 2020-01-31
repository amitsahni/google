package com.google

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserInfo
import com.google.gson.Gson


val googleAuth = FirebaseAuth.getInstance()

internal var account: GoogleSignInAccount? = null
    private set

internal var gso: GoogleSignInOptions? = null

var clientId: String = ""
    set(value) {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(value)
                .requestEmail()
                .build()
    }

fun Activity.login(requestCode: Int) {
    val client = GoogleSignIn.getClient(this, gso!!)
    val signInIntent = client.signInIntent
    startActivityForResult(signInIntent, requestCode)
}

fun onActivityResult(data: Intent?, success: Boolean.() -> Unit) {
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

val googleProfile: GoogleSignInAccount?
    get() {
        return account
    }

fun profile(f: (Triple<UserInfo?, Exception?, Unit?>) -> Unit) {
    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
    googleAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    googleAuth.currentUser?.let {
                        it.providerData.forEach { user ->
                            if (user.providerId.equals("google.com")) {
                                Log.d("Json = ", Gson().toJson(user))
                                f(Triple(user, null, null))
                            }
                        }
                    }
                } else {
                    f(Triple(null, it.exception, null))
                }
            }.addOnCanceledListener {
                f(Triple(null, null, Unit))
            }.addOnFailureListener {
                f(Triple(null, null, Unit))
            }
}

fun logOut() {
    googleAuth.signOut()
}


val googleToken: String?
    get() {
        return account?.idToken
    }

val googleUser: UserInfo?
    get() {
        googleAuth.currentUser?.let {
            it.providerData.forEach {
                if (it.providerId == "google.com") {
                    return it
                }
            }
        }
        return null
    }