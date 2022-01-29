package com.siera.users.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.*
import com.siera.users.R
import com.siera.users.model.User
import com.siera.users.view.UserListAdapter.NoteHolder

class UserListAdapter : ListAdapter<User, NoteHolder>(DIFF_CALLBACK) {
    private var listener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentUser = getItem(position)
        holder.textViewName.text = currentUser!!.name
        holder.textViewNumber.text = currentUser.number
    }

    fun getNoteAt(position: Int): User? {
        return getItem(position)
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView
        val textViewNumber: TextView

        init {
            textViewName = itemView.findViewById(R.id.text_view_title)
            textViewNumber = itemView.findViewById(R.id.text_view_description)
            itemView.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(getItem(position))
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(user: User?)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<User> =
            object : DiffUtil.ItemCallback<User>() {
                override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                    return false
                }

                override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                    return false
                }
            }
    }
}