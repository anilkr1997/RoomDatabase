package com.nic.roomdatabase.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nic.roomdatabase.Adopter.TaskAdopter
import com.nic.roomdatabase.Adopter.TaskAdopter.Deletetask
import com.nic.roomdatabase.R
import com.nic.roomdatabase.Roomdbdirectery.TaskDatabase
import com.nic.roomdatabase.Roomdbdirectery.TaskItem
import com.nic.roomdatabase.databinding.FragmentListofNoteBinding
import com.nic.roomdatabase.toast
import kotlinx.coroutines.*

class ListofNoteFragment : BaseFragment(), Deletetask {


    private lateinit var listofNoteBinding: FragmentListofNoteBinding
    private val binding get() = listofNoteBinding

    private lateinit var viewModel: ListofNoteViewModel
    private lateinit var arrayList: List<TaskItem>
    private lateinit var taskAdopter: TaskAdopter;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        listofNoteBinding = FragmentListofNoteBinding.inflate(inflater, container, false)
        return listofNoteBinding.root
    }


    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ListofNoteViewModel::class.java]

        binding.recycleview.apply {
            layoutManager =
                LinearLayoutManager(context?.applicationContext, RecyclerView.VERTICAL, true)
            this.setHasFixedSize(true)

        }

        // coroutine implementation
        getdatafrombd()


        binding.febAdd.setOnClickListener {
            findNavController().navigate(R.id.action_mobile_navigation_to_addNoteFragment)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getdatafrombd() {


        GlobalScope.launch {


            withContext(Dispatchers.Main) {
                context?.let {
                    if (TaskDatabase(it).TaskDao().getAllTask().isNotEmpty()) {

                        binding.recycleview.adapter = TaskAdopter(
                            requireContext(),
                            TaskDatabase(it).TaskDao().getAllTask(),
                            this@ListofNoteFragment
                        )

                    }
                }

            }



            Log.e("TAG", "onViewCreated: " + TaskDatabase(requireContext()).TaskDao().getAllTask())
        }

        binding.recycleview.adapter?.notifyDataSetChanged()
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun deletitem(taskItem: TaskItem) {

        GlobalScope.launch {
            TaskDatabase(context!!).TaskDao().deletitem(taskItem)


        }
        getdatafrombd()
        context!!.toast("Data Deleted Successfully")
    }
}