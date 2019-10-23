package com.m7amdelbana.firebasekotlin.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.m7amdelbana.firebasekotlin.R
import com.m7amdelbana.firebasekotlin.view.main.MainActivity

class LoginActivity : AppCompatActivity() {

    lateinit var edtEmail: EditText
    lateinit var edtPassword: EditText
    lateinit var btnSignIn: Button
    lateinit var tVSignUp: TextView

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUI()

        mAuth = FirebaseAuth.getInstance()

        btnSignIn.setOnClickListener {
            // Validate
            signInUser(
                email = edtEmail.text.toString().trim(),
                password = edtPassword.text.toString().trim()
            )
        }

        tVSignUp.setOnClickListener {
            navigateSignUp()
        }
    }

    private fun initUI() {
        edtEmail = findViewById(R.id.email_editText)
        edtPassword = findViewById(R.id.password_editText)
        btnSignIn = findViewById(R.id.sign_in_button)
        tVSignUp = findViewById(R.id.sign_up_textView)

    }

    private fun signInUser(email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    val user = mAuth!!.currentUser
                    if (user != null) {
                        navigateToMain()
                    }
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateSignUp() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
