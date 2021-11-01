package com.example.assesment.fragments


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.assesment.R
import com.example.assesment.ViewModel.MainViewModel
import com.example.assesment.adapters.RVadapter
import com.example.assesment.classes.services
import com.example.assesment.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {



    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!


    private lateinit var adapter:RVadapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //setHasOptionsMenu(true)
        viewModel= ViewModelProvider(requireActivity()).get(MainViewModel::class.java)


        Log.e("List frag","${viewModel.list}")
        adapter = RVadapter(requireActivity())
        adapter.setOnClickListener(object :RVadapter.ONCLICK{
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onClick(pos:Int,service: services, view: View) {
//                viewModel.changeBoolean(pos)
//              //  adapter.submitList(ArrayList<services>()) //if reference of the list is same it does nothing
//
//                adapter.submitList(viewModel.list.toMutableList())
                Toast.makeText(context,"Coming Soon",Toast.LENGTH_SHORT).show()
            }

        })

        viewModel.listHome.value?.let { adapter.submitList(it) }
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.recView.layoutManager=GridLayoutManager(context,4)

        binding.recView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.mainToolbar.inflateMenu(R.menu.menu_main)
        binding.mainToolbar.setTitle(R.string.app_name)
        binding.mainToolbar.setTitleTextColor(resources.getColor(R.color.black))
        binding.mainToolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.edit_icons -> {
                   // Toast.makeText(context, "Clicking Works", Toast.LENGTH_SHORT).show()
                    viewModel.selectedServiceShow()
                    val fragmentManager: FragmentManager? = activity?.supportFragmentManager
                    val fragmentTransaction: FragmentTransaction =
                        fragmentManager!!.beginTransaction()
                    val fragment2 = EditFragment()
                    fragmentTransaction.addToBackStack("First_Fragment")
                    fragmentTransaction.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out,R.anim.push_down_in,R.anim.push_down_out).hide(this@HomeFragment)

                    fragmentTransaction.add(android.R.id.content, fragment2)
                    //viewModel.set_list()



                    fragmentTransaction.commit()
                    true
                }
                else -> false
            }

        }

        viewModel.listHome.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                binding.tvFragHome.visibility = View.VISIBLE
                binding.recView.visibility= View.INVISIBLE
            }else{
                binding.tvFragHome.visibility = View.INVISIBLE
                binding.recView.visibility= View.VISIBLE
                viewModel.listHome.value?.let { it1 -> adapter.submitList(it1) }
                adapter.notifyDataSetChanged()
            }
        }



    }





}