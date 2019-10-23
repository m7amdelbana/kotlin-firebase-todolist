package com.m7amdelbana.firebasekotlin.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.FirebaseDatabase
import com.m7amdelbana.firebasekotlin.R
import com.m7amdelbana.firebasekotlin.network.model.Todo
import com.m7amdelbana.firebasekotlin.network.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var todos: ArrayList<Todo>

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()

        mAuth = FirebaseAuth.getInstance()

        val user = mAuth!!.currentUser
        if (user != null) {
            getCurrentUser(user.uid)
            getMyTodo(user.uid)
        }
    }

    private fun initUI() {
        todos = ArrayList()
        recyclerView = findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
    }

    private fun getCurrentUser(userId: String) {
        val database = FirebaseDatabase.getInstance()
        val dbRef = database.getReference("Users").child(userId)
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userObject = dataSnapshot.getValue(User::class.java)
                if (userObject != null)
                    Toast.makeText(
                        this@MainActivity,
                        "Welcome back! ${userObject.username}",
                        Toast.LENGTH_LONG
                    ).show()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@MainActivity, "Failed to read user.",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        })
    }

    private fun getMyTodo(userId: String) {
        val database = FirebaseDatabase.getInstance()
        val dbRef = database.getReference("ToDo").child(userId)

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val todo = snapshot.getValue(Todo::class.java)
                    todo?.let { todos.add(it) }
                }

                todoAdapter = TodoAdapter(todos)
                recyclerView.adapter = todoAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    this@MainActivity, "Failed to load todo list.",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
