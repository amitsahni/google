package org.google

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.GoogleManager
import com.google.config
import com.google.login
import com.google.profile
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        config("872446504976-l8pa7tp4nrc0v07tqa68bu7r6pqsvrtp.apps.googleusercontent.com")
        google.setOnClickListener {
            val user = GoogleManager.user
            if (user == null) {
                login(1000)
            } else {
                Log.i(
                    localClassName + "Google",
                    user.displayName + " " + user.email + "" + user.phoneNumber
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1000) {
                GoogleManager.onActivityResult(data!!) {
                    if (this)
                        profile({
                            Log.i(
                                localClassName + "Google",
                                "${it.displayName} ${it.email} ${it.phoneNumber}"
                            )
                        }, {
                            it.printStackTrace()
                        }, {
                            Log.i(
                                localClassName ,"Cancelled"
                            )
                        })
                }
            }
        }

    }
}
