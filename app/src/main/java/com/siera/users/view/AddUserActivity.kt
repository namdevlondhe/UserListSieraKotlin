package com.siera.users.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.siera.users.R

class AddUserActivity : AppCompatActivity() {
    private var editTextName: EditText? = null
    private var editTextNumber: EditText? = null
    private var buttonSubmit: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        setUpView()
        addButtonListener()
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)
        val intent = intent
        if (intent.hasExtra(EXTRA_ID)) {
            title = getString(R.string.str_edit_user)
            editTextName!!.setText(intent.getStringExtra(EXTRA_NAME))
            editTextNumber!!.setText(intent.getStringExtra(EXTRA_NUMBER))
        } else {
            title = getString(R.string.str_add_user)
        }
    }

    private fun addButtonListener() {
        buttonSubmit!!.setOnClickListener { saveNote() }
    }

    private fun setUpView() {
        editTextName = findViewById(R.id.edit_text_name)
        editTextNumber = findViewById(R.id.edit_text_number)
        buttonSubmit = findViewById(R.id.button_submit)
    }

    private fun saveNote() {
        val name = editTextName!!.text.toString()
        val number = editTextNumber!!.text.toString()
        if (name.trim { it <= ' ' }.isEmpty()) {
            editTextName!!.error = getString(R.string.str_error_type_name_and_number)
            Toast.makeText(
                this,
                getString(R.string.str_error_type_name_and_number),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (number.trim { it <= ' ' }.isEmpty()) {
            editTextNumber!!.error = getString(R.string.str_error_type_number)
            Toast.makeText(this, getString(R.string.str_error_type_number), Toast.LENGTH_SHORT)
                .show()
            return
        }
        val data = Intent()
        data.putExtra(EXTRA_NAME, name)
        data.putExtra(EXTRA_NUMBER, number)
        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }
        setResult(RESULT_OK, data)
        finish()
    }

    companion object {
        const val EXTRA_ID = "com.siera.EXTRA_ID"
        const val EXTRA_NAME = "com.siera.EXTRA_NAME"
        const val EXTRA_NUMBER = "com.siera.EXTRA_NUMBER"
    }
}