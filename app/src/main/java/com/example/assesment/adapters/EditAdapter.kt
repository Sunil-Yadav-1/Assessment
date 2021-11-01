package com.example.assesment.adapters

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.assesment.R
import com.example.assesment.classes.services
import com.example.assesment.databinding.ListViewBinding

class EditAdapter(val activity: Activity): RecyclerView.Adapter<EditAdapter.ViewHolder>() {

    private lateinit var binding:ListViewBinding
    private var list : List<services> = ArrayList()

    private var onClickListener: ONCLICK?= null

    class ViewHolder(val binding: ListViewBinding):RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ListViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        Log.d("size","$list")
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        val service = list[position]
        Log.d("Adapter","$service")
        binding.tvIcon.text = service.name

        if(service.isWanted){
            binding.imageViewIcon.foreground = ResourcesCompat.getDrawable(activity.resources,
                R.drawable.image_overlay,null)
        }else{
            binding.imageViewIcon.foreground=null
        }

        if(onClickListener != null){
            binding.tvIcon.setOnClickListener{
                onClickListener!!.onClick(position,service,itemView.findViewById(R.id.imageView_icon))
            }

            binding.imageViewIcon.setOnClickListener{
                onClickListener!!.onClick(position,service,itemView.findViewById<ImageView>(R.id.imageView_icon))
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun submitList(list:List<services>){
        val oldList:List<services> = this.list
        val diffResult : DiffUtil.DiffResult= DiffUtil.calculateDiff(
            ListItemDiffCallback(oldList,list)
        )
        this.list = list

        diffResult.dispatchUpdatesTo(this)
    }


    interface ONCLICK{

        fun onClick(pos:Int, service: services, view: View)
    }

    fun setOnClickListener(listener:ONCLICK){
        this.onClickListener = listener
    }

    class ListItemDiffCallback(var oldList:List<services>,
                               var newList : List<services>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList.get(oldItemPosition).id ==newList.get(newItemPosition).id)
                    &&(oldList.get(oldItemPosition).name ==newList.get(newItemPosition).name)
                    &&(oldList.get(oldItemPosition).isWanted ==newList.get(newItemPosition).isWanted)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList.get(oldItemPosition).id ==newList.get(newItemPosition).id)
                    &&(oldList.get(oldItemPosition).name ==newList.get(newItemPosition).name)
                    &&(oldList.get(oldItemPosition).isWanted ==newList.get(newItemPosition).isWanted)
        }

    }
}