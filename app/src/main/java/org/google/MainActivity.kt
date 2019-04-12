package org.google

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.GoogleConfiguration
import com.google.GoogleConnect
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GoogleConfiguration.clientId("872446504976-l8pa7tp4nrc0v07tqa68bu7r6pqsvrtp.apps.googleusercontent.com")
            .build()
        google.setOnClickListener {
            val user = GoogleConnect.user
            if (user == null) {
                GoogleConnect.with()
                    .login(this@MainActivity, 1000)
                    .build()
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
                GoogleConnect.onActivityResult(data!!) {
                    if (this)
                        GoogleConnect.with()
                            .profile()
                            .success {
                                Log.i(
                                    localClassName + "Google",
                                    "$displayName $email $phoneNumber"
                                )
                            }
                            .build()
                    Unit
                }
            }
        }

    }
}
