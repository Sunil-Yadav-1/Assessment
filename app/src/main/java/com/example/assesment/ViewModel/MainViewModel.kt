package com.example.assesment.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assesment.classes.services

class MainViewModel:ViewModel() {
   private var _list:ArrayList<services> = ArrayList()



    val list : ArrayList<services> get()= _list

    private var _listHome : MutableLiveData<ArrayList<services>> = MutableLiveData(ArrayList())

    val listHome:LiveData<ArrayList<services>> get() = _listHome

    fun set_list(){
        for(i in 1..20){
            _list.add(services(i,"service $i",false))
        }
    }

    fun setListHome(list:ArrayList<services>){
        _listHome.value = list
    }

    fun changeBoolean(pos:Int){
        val service = _list[pos];
        val otherService = services(service.id,service.name,!service.isWanted);
        _list.remove(service)
        _list.add(pos,otherService)

        Log.e("changed list","${list[pos].isWanted}")
    }

    fun setAllBoolFalse(){
        for(service in _list){
            service.isWanted=false
        }
    }

    fun setSelectedService(){
        var tempList:ArrayList<services> = ArrayList()
        for(service in _list){
            if(service.isWanted){
                tempList.add(service)
            }
        }
        _listHome.value=tempList
    }


    fun selectedServiceShow(){
        val tempList = _listHome.value
        if(tempList != null && tempList.isNotEmpty()){
            for(service in tempList){
                for(svc in _list){
                    if(svc.id== service.id){
                        svc.isWanted=true
                    }
                }
            }
        }
    }

}