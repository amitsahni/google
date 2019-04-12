package com.google

import android.app.Activity

class Builder {

    fun login(activity: Activity, requestCode: Int): RequestBuilder.LoginBuilder {
        return RequestBuilder.LoginBuilder(activity, requestCode)
    }


    fun profile(): RequestBuilder.ProfileBuilder {
        return RequestBuilder.ProfileBuilder()
    }

    fun logOut() {
        GoogleConnect.auth.signOut()
    }
}