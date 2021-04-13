package com.paulo.teixeira.robint.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.paulo.teixeira.robint.widget.OnItemClickListener

abstract class  BaseAdapter<T, VB : ViewBinding> :
    RecyclerView.Adapter<BaseAdapter<T, VB>.ViewHolder>() {
    
    private val mDataList: MutableList<T> by lazy { mutableListOf() }
    private var mOnItemClickListener: OnItemClickListener<T>? = null
    
    var onItemClickListener
        get() = mOnItemClickListener
        set(value) {
            mOnItemClickListener = value
        }
    
    abstract val bindingInflater: (inflater: LayoutInflater, viewGroup: ViewGroup) -> VB
    
    inner class ViewHolder(val binding: VB) :
        RecyclerView.ViewHolder(binding.root)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(bindingInflater.invoke(LayoutInflater.from(parent.context), parent))
    }
    
    abstract fun onBindViewHolderBase(holder: ViewHolder?, position: Int, item: T)
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        this.onBindViewHolderBase(holder, position, data[position])
        
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.invoke(
                it, holder.adapterPosition,
                getItem(holder.adapterPosition)
            )
        }
    }
    
    override fun getItemCount() = mDataList.size

    protected fun getItem(index: Int) = mDataList[index]

    val data: MutableList<T>
        get() = mDataList

    val lastIndex: Int
        get() = mDataList.lastIndex

    val isEmpty: Boolean
        get() = data.isEmpty()

    fun getItemRange(startIndex: Int) = getItemRange(startIndex, mDataList.size)

    fun getItemRange(startIndex: Int, endIndex: Int) = mDataList.subList(startIndex, endIndex)

    fun addItem(item: T) {
        mDataList.add(item)
        notifyItemInserted(if (itemCount > 0) mDataList.lastIndex else 0)
    }

    fun addItem(position: Int, item: T) {
        mDataList.add(position, item)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        if (mDataList.isEmpty()) {
            return
        }

        this.mDataList.removeAt(position)
        this.notifyItemRemoved(position)
    }

    fun clearData() {
        this.mDataList.clear()
        this.notifyDataSetChanged()
    }

    fun addData(list: List<T>) {
        val firstItemPosition = this.mDataList.lastIndex + 1
        this.mDataList.addAll(list)
        this.notifyItemRangeInserted(firstItemPosition, list.size)
    }
}