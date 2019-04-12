package com.google

import android.app.Activity
import com.google.callback.OnCancel
import com.google.callback.OnError
import com.google.callback.OnSuccess
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserInfo

class RequestBuilder {

    class ProfileBuilder {
        private var successCallback: OnSuccess<UserInfo>? = null
        private var errorCallback: OnError<Exception>? = null
        private var cancelCallback: OnCancel? = null

        fun success(success: UserInfo.() -> Unit): ProfileBuilder {
            successCallback = object : OnSuccess<UserInfo> {
                override fun onSuccess(result: UserInfo) {
                    success(result)
                }
            }
            return this
        }

        fun error(e: Exception.() -> Unit): ProfileBuilder {
            errorCallback = object : OnError<Exception> {
                override fun onError(error: Exception) {
                    e(error)
                }
            }
            return this
        }

        fun cancel(cancel: () -> Unit): ProfileBuilder {
            cancelCallback = object : OnCancel {
                override fun onCancel() {
                    cancel()
                }
            }
            return this
        }


        fun build() {
            val credential = GoogleAuthProvider.getCredential(GoogleConnect.account?.idToken, null)
            GoogleConnect.auth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            GoogleConnect.auth.currentUser?.let {
                                it.providerData.forEach { user ->
                                    if (user.providerId.equals("google.com")) {
                                        successCallback?.onSuccess(user)
                                    }
                                }
                            }
                        } else {
                            it.exception?.let {
                                errorCallback?.onError(it)
                            }
                        }
                    }.addOnCanceledListener {
                        cancelCallback?.onCancel()
                    }.addOnFailureListener {
                        errorCallback?.onError(it)
                    }
        }
    }

    class LoginBuilder(private val activity: Activity, private val requestCode: Int) {

        fun build() {
            val client = GoogleSignIn.getClient(activity, GoogleConfiguration.gso!!)
            val signInIntent = client.signInIntent
            activity.startActivityForResult(signInIntent, requestCode)
        }


    }
}