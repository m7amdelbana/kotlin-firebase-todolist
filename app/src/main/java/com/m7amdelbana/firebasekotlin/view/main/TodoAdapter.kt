package com.m7amdelbana.firebasekotlin.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.m7amdelbana.firebasekotlin.R
import com.m7amdelbana.firebasekotlin.network.model.Todo

class TodoAdapter(private val todos: ArrayList<Todo>) : RecyclerView.Adapter<TodoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        val inflatedView = parent.inflate(R.layout.item_todo, false)
        return TodoHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        val item = todos[position]
        holder.bindLayout(item)
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun getItemCount() = todos.size
}