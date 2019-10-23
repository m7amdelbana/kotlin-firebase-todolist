package com.m7amdelbana.firebasekotlin.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.m7amdelbana.firebasekotlin.R
import com.m7amdelbana.firebasekotlin.network.model.User
import com.m7amdelbana.firebasekotlin.view.main.MainActivity


class RegisterActivity : AppCompatActivity() {

    lateinit var edtUsername: EditText
    lateinit var edtEmail: EditText
    lateinit var edtPassword: EditText
    lateinit var btnSignUp: Button

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initUI()

        mAuth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            // Validate
            signUpUser(
                username = edtUsername.text.toString().trim(),
                email = edtEmail.text.toString().trim(),
                password = edtPassword.text.toString().trim()
            )
        }
    }

    private fun initUI() {
        edtUsername = findViewById(R.id.username_editText)
        edtEmail = findViewById(R.id.email_editText)
        edtPassword = findViewById(R.id.password_editText)
        btnSignUp = findViewById(R.id.sign_up_button)

    }

    private fun signUpUser(username: String, email: String, password: String) {
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    val user = mAuth!!.currentUser
                    if (user != null) {
                        val userObject = User(username, email)
                        saveUserToDB(user.uid, userObject)
                    }
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun saveUserToDB(userId: String, user: User) {
        val database = FirebaseDatabase.getInstance()
        val dbRef = database.getReference("Users")
        dbRef.child(userId).setValue(user).addOnSuccessListener {
            navigateToMain()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
