package com.m7amdelbana.firebasekotlin.view.main

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.m7amdelbana.firebasekotlin.network.model.Todo
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var todo: Todo? = null

    private var cbDone: CheckBox = view.todo_checkBox
    private var tvTitle: TextView = view.todo_textView

    fun bindLayout(todo: Todo) {
        this.todo = todo
        tvTitle.text = todo.title
        todo.done?.let { cbDone.isChecked = it }
    }
}