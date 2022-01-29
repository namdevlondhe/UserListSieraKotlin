package com.siera.users.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.siera.users.R
import com.siera.users.model.User
import com.siera.users.view.AddUserActivity
import com.siera.users.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    private var userViewModel: UserViewModel? = null
    private var textViewNoUsers: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewNoUsers = findViewById(R.id.textViewNoUsers)

        //Floating action button for adding users
        val buttonAddNote = findViewById<FloatingActionButton>(R.id.button_add_note)
        buttonAddNote.setOnClickListener {
            val intent = Intent(this@MainActivity, AddUserActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }

        //Listing all the users
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        val adapter = UserListAdapter()
        recyclerView.adapter = adapter
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel!!.allUsers?.observe(this) { users ->
            if (users!!.isNotEmpty()) {
                recyclerView.visibility = View.VISIBLE
                textViewNoUsers!!.visibility = View.GONE
                adapter.submitList(users)
            } else {
                recyclerView.visibility = View.GONE
                textViewNoUsers!!.visibility = View.VISIBLE
            }
        }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                userViewModel!!.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.str_msg_user_deleted),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }).attachToRecyclerView(recyclerView)

        adapter.setOnItemClickListener(object : UserListAdapter.OnItemClickListener {

            override fun onItemClick(user: User?) {
                val intent = Intent(this@MainActivity, AddUserActivity::class.java)
                intent.putExtra(AddUserActivity.EXTRA_ID, user?.id)
                intent.putExtra(AddUserActivity.EXTRA_NAME, user?.name)
                intent.putExtra(AddUserActivity.EXTRA_NUMBER, user?.number)
                startActivityForResult(intent, EDIT_NOTE_REQUEST)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            val title = data!!.getStringExtra(AddUserActivity.Companion.EXTRA_NAME)
            val description = data.getStringExtra(AddUserActivity.Companion.EXTRA_NUMBER)
            val user = User(title, description)
            userViewModel!!.insert(user)
            Toast.makeText(this, getString(R.string.str_msg_users_saved), Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            val id = data!!.getIntExtra(AddUserActivity.Companion.EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(
                    this,
                    getString(R.string.str_msg_users_cant_update),
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            val title = data.getStringExtra(AddUserActivity.Companion.EXTRA_NAME)
            val description = data.getStringExtra(AddUserActivity.Companion.EXTRA_NUMBER)
            val user = User(title, description)
            user.id = id
            userViewModel!!.update(user)
            Toast.makeText(this, getString(R.string.str_msg_users_updated), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(this, getString(R.string.str_users_not_saved), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_notes -> {
                userViewModel!!.deleteAllUsers()
                Toast.makeText(this, getString(R.string.str_msg_users_deleted), Toast.LENGTH_SHORT)
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val ADD_NOTE_REQUEST = 1
        const val EDIT_NOTE_REQUEST = 2
    }
}