package com.google

import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserInfo


fun config(clientId: String) {
    GoogleManager.gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(clientId)
        .requestEmail()
        .build()
}

fun Activity.login(requestCode: Int) {
    val client = GoogleSignIn.getClient(this, GoogleManager.gso!!)
    val signInIntent = client.signInIntent
    startActivityForResult(signInIntent, requestCode)
}

fun profile(s: (UserInfo) -> Unit, e: (Exception) -> Unit, c: () -> Unit) {
    val credential = GoogleAuthProvider.getCredential(GoogleManager.account?.idToken, null)
    GoogleManager.auth.signInWithCredential(credential)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                GoogleManager.auth.currentUser?.let {
                    it.providerData.forEach { user ->
                        if (user.providerId.equals("google.com")) {
                            s(user)
                        }
                    }
                }
            } else {
                it.exception?.let {
                    e(it)
                }
            }
        }.addOnCanceledListener {
            c()
        }.addOnFailureListener {
            e(it)
        }
}

fun logOut() {
    GoogleManager.auth.signOut()
}