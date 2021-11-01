package com.example.assesment.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.assesment.R
import com.example.assesment.ViewModel.MainViewModel
import com.example.assesment.adapters.EditAdapter
import com.example.assesment.adapters.RVadapter
import com.example.assesment.classes.services
import com.example.assesment.databinding.FragmentEditBinding
import com.example.assesment.databinding.FragmentHomeBinding


class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null

    private val binding get() = _binding!!

    private lateinit var list:List<services>
    private lateinit var adapter: EditAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel= ViewModelProvider(requireActivity()).get(MainViewModel::class.java)


        Log.e("List frag","${viewModel.list}")
        adapter = EditAdapter(requireActivity())
        adapter.setOnClickListener(object :EditAdapter.ONCLICK{
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onClick(pos:Int,service: services, view: View) {
                viewModel.changeBoolean(pos)
              //  adapter.submitList(ArrayList<services>()) //if reference of the list is same it does nothing

//                adapter.submitList(viewModel.list.toMutableList())
                adapter.notifyDataSetChanged()
                //Toast.makeText(context,"Coming Soon", Toast.LENGTH_SHORT).show()
            }

        })

        adapter.submitList(viewModel.list)
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.recViewEdit.layoutManager= GridLayoutManager(context,4)

        binding.recViewEdit.adapter = adapter

        binding.btDone.setOnClickListener{
            viewModel.setListHome(ArrayList<services>())
            viewModel.setSelectedService()
            viewModel.setAllBoolFalse()
            requireActivity().onBackPressed()

        }

        binding.btCancel.setOnClickListener{
            viewModel.setAllBoolFalse()
            requireActivity().onBackPressed()
        }


        return view
    }


}