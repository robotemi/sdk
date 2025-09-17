package com.robotemi.sdk.sample

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.core.widget.doAfterTextChanged

class EditDialog(
    context: Context, private val locations: MutableList<String>,
    private val editorActionListener: EditorActionListener
) : Dialog(context) {
    private lateinit var listView: ListView
    private lateinit var confirmBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit_locations)
        listView = findViewById(R.id.listView)
        val adapter = EditableListAdapter(
            context,
            locations
        )
        listView.adapter = adapter
        confirmBtn = findViewById(R.id.confirmBtn)
        confirmBtn.setOnClickListener {
            editorActionListener.editCompleted(this, adapter.oldText, adapter.newText)
        }
    }

    override fun dismiss() {
        val view = currentFocus
        if (view is EditText) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        super.dismiss()
    }
    class EditableListAdapter(
        private val context: Context,
        private val dataList: MutableList<String>
    ) : BaseAdapter() {
        var newText: String = ""
        var oldText: String = ""

        override fun getCount(): Int = dataList.size
        override fun getItem(position: Int): Any = dataList[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.item_editable_dialog_row, parent, false)

            val editText = view.findViewById<EditText>(R.id.name)
            editText.setText(dataList[position])

            editText.doAfterTextChanged {
                oldText = dataList[position]
                newText = it.toString()
            }
            return view
        }
    }

    interface EditorActionListener {
        fun editCompleted(editDialog: EditDialog, oldName: String, newName: String)
    }
}