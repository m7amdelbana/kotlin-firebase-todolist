package com.m7amdelbana.firebasekotlin.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.m7amdelbana.firebasekotlin.R
import com.m7amdelbana.firebasekotlin.view.main.MainActivity
import com.m7amdelbana.firebasekotlin.view.auth.LoginActivity

class SplashActivity : AppCompatActivity() {

        private var handler: Handler? = null
        private val SPLASH_DELAY: Long = 3000

        private var mAuth: FirebaseAuth? = null

        private val runnable: Runnable = Runnable {
            if (!isFinishing) {
                // Check if user is signed in (non-null).
                val currentUser = mAuth!!.currentUser

                val intent: Intent?

                intent = if (currentUser == null) {
                    Intent(this, LoginActivity::class.java)
                } else {
                    Intent(this, MainActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splash)

            mAuth = FirebaseAuth.getInstance()
        }

        public override fun onStart() {
            super.onStart()

            handler = Handler()
            handler!!.postDelayed(runnable, SPLASH_DELAY)
        }
    }
