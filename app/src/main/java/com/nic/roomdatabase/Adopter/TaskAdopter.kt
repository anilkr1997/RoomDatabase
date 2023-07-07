package com.nic.roomdatabase.Adopter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nic.roomdatabase.Fragment.ListofNoteFragment
import com.nic.roomdatabase.Fragment.ListofNoteFragmentDirections
import com.nic.roomdatabase.Roomdbdirectery.TaskItem
import com.nic.roomdatabase.databinding.TasklistitemBinding

class TaskAdopter(
    private val listofNoteFragment: Context,
    private val getallItemlist: List<TaskItem>,
  private val listofNoteFragment1: ListofNoteFragment
) : RecyclerView.Adapter<TaskAdopter.MyViewHolder>() {

    class MyViewHolder(val binding: TasklistitemBinding) : RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            TasklistitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return getallItemlist.size
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.titel.setText(getallItemlist.get(position).taskname)
        holder.binding.descrepthion.setText(getallItemlist.get(position).TaskDescription)
        holder.binding.delet.setOnClickListener {
            listofNoteFragment1.deletitem(getallItemlist.get(position))
            notifyItemRemoved(position)
            notifyDataSetChanged()

        }

        holder.binding.root.setOnClickListener {

            val action = ListofNoteFragmentDirections.actionMobileNavigationToAddNoteFragment().setTaskItem(getallItemlist[position])


            findNavController(it).navigate(action)
        }
    }
 public   interface Deletetask{
        fun deletitem(taskItem: TaskItem)
    }
}