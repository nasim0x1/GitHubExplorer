package com.github.expo.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.github.expo.data.model.UserRepositoryItem
import com.github.expo.databinding.RepositoryItemBinding
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.ContextCompat.startActivity











/* 
 * Created by nasim on 1/17/22  
 */
class RepositoryAdapter(val context: Context, val data: List<UserRepositoryItem>) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RepositoryItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.repoName.text = data[position].name.toString()
        holder.binding.lang.text = if (data[position].name == "") "Language: Not found" else "Language: " + data[position].language
        holder.binding.desc.text = if (data[position].description == null) "Description: Not written by user" else "Description:\n"+data[position].description.toString()

        holder.binding.root.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data[position].svn_url))
            context.startActivity(intent)


        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}

class ViewHolder(val binding: RepositoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

}