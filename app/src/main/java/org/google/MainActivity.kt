package org.google

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clientId = "872446504976-l8pa7tp4nrc0v07tqa68bu7r6pqsvrtp.apps.googleusercontent.com"
        google.setOnClickListener {
            val user = lastUserSignIn
            if (user == null) {
                googleLogin(1000)
            } else {
                Log.i(
                        localClassName + "Google",
                        user.displayName + " " + user.email
                )
                googleLogOut()
//                googleRevokeAccess()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1000) {
                onActivityResult(data) {
                    if (this) {
                        googleToken {
                            Log.i("Token = ", it.toString())
                        }
                        Log.i("Token Profile = ", googleProfile?.idToken ?: "")
                        Log.d("Profile = ", googleProfile?.toString())
                    }
                }
            }
        }

    }
}
