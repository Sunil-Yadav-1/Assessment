package com.example.assesment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import android.widget.Toast
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.assesment.Contants.Constant
import com.example.assesment.R
import com.example.assesment.ViewModel.MainViewModel
import com.example.assesment.classes.services
import com.example.assesment.databinding.ActivityMainBinding
import com.example.assesment.fragments.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var viewModel:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.set_list()
        Log.e("activity list","${viewModel.list}")
        Log.d("ViewModel",viewModel.list.size.toString())
        setContentView(binding.root)
        if(savedInstanceState==null){
//            val bundle =Bundle().apply {
//                putParcelableArrayList(Constant.LIST,ArrayList<Parcelable>(viewModel.list))
//            }
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<HomeFragment>(R.id.frame_container)
            }
        }


    }




}