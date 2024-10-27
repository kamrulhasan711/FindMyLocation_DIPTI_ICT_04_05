package com.example.findmylocation_dipti_ict_04_05.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.findmylocation_dipti_ict_04_05.databinding.ItemUserDiptiIct0405Binding
import com.example.findmylocation_dipti_ict_04_05.model.User_DIPTI_ICT_04_05

class UserAdapter_DIPTI_ICT_04_05(private var userDIPTIICT0405List: List<User_DIPTI_ICT_04_05>): RecyclerView.Adapter<UserAdapter_DIPTI_ICT_04_05.UserViewHolder>() {
    class UserViewHolder(private val binding: ItemUserDiptiIct0405Binding): RecyclerView.ViewHolder(binding.root){
        fun bind(userDIPTIICT0405: User_DIPTI_ICT_04_05){
            binding.apply {
                displayNameTxt.text = userDIPTIICT0405.displayname
                emailTxt.text = userDIPTIICT0405.email
                locationTxt.text = userDIPTIICT0405.location
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserDiptiIct0405Binding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return userDIPTIICT0405List.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userDIPTIICT0405List[position]

        holder.bind(user)

    }
    fun updateData(newList: List<User_DIPTI_ICT_04_05>) {
        userDIPTIICT0405List = newList
        notifyDataSetChanged()
    }
}