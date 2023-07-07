package com.nic.roomdatabase.Fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nic.roomdatabase.R
import com.nic.roomdatabase.Roomdbdirectery.TaskDatabase
import com.nic.roomdatabase.Roomdbdirectery.TaskItem
import com.nic.roomdatabase.databinding.FragmentAddNoteBinding
import com.nic.roomdatabase.toast
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddNoteFragment : BaseFragment() {
    private var utaskItem: TaskItem? = null

    companion object {
        fun newInstance() = AddNoteFragment()
    }

    private lateinit var addNoteBinding: FragmentAddNoteBinding
    private val binding get() = addNoteBinding

    private lateinit var viewModel: AddNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        addNoteBinding = FragmentAddNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AddNoteViewModel::class.java]
        arguments?.let {
            utaskItem = AddNoteFragmentArgs.fromBundle(it).taskItem
            binding.etName.setText(utaskItem?.taskname)
            binding.etDescreptiuon.setText(utaskItem?.TaskDescription)
        }

        binding.btnAdd.setOnClickListener(View.OnClickListener {
            if (validation()) {
                GlobalScope.launch {


                    context?.let {
                        val taskItem = TaskItem(
                            binding.etName.text.toString(),
                            binding.etDescreptiuon.text.toString(),
                            0
                        );
                        if (utaskItem == null) {

                            TaskDatabase(it).TaskDao().inserttaskItem(taskItem)


                        } else {
                             taskItem.id= utaskItem?.id!!
                            TaskDatabase(it).TaskDao().updatetak(taskItem)



                        }

                    }


                }
                if(utaskItem==null){
                    context?.toast("Data Insert Successfully")
                }else{
                    context?.toast("Data Updated Successfully")
                }

                findNavController().navigate(R.id.action_addNoteFragment_to_home_fragment)


            }
        })


    }


// add data uesing  AsyncTask
//    private fun addtask() {
//        class datainsert : AsyncTask<Void,Void,Void>(){
//            override fun doInBackground(vararg params: Void?): Void? {
//                val taskItem=TaskItem(binding.etName.text.toString(),binding.etDescreptiuon.text.toString(),0);
//                TaskDatabase(requireContext()).TaskDao().inserttaskItem(taskItem)
//               return null
//            }
//
//            override fun onPostExecute(result: Void?) {
//                super.onPostExecute(result)
//                Toast.makeText(context,"Save data",Toast.LENGTH_LONG).show()
//                binding.etName.text?.clear()
//                binding.etDescreptiuon.text?.clear()
//            }
//        }
//        datainsert().execute()
//
//    }

    private fun validation(): Boolean {
        if (TextUtils.isEmpty(binding.etName.text)) {
            context?.toast("Please enter task title")


            return false
        } else if (TextUtils.isEmpty(binding.etDescreptiuon.text)) {
            context?.toast("Please enter task description")



            return false
        }
        return true

    }

}