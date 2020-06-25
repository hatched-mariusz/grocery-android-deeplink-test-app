package com.hatchedlabs.grocery.deeplink

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.deep_link_example_item_layout.view.*

class ExamplesAdapter(val listener: OnExampleClickListener) : RecyclerView.Adapter<ExamplesAdapter.ExampleViewHolder>() {

    val examples = mutableListOf<LinkExample>()

    fun setData(list:List<LinkExample>){
        examples.clear()
        examples.addAll(list)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val view = LinearLayout.inflate(parent.context, R.layout.deep_link_example_item_layout, null)
        return ExampleViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = examples.size


    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val linkExample = examples[position]
        holder.itemView.example_title.text = linkExample.title
        holder.itemView.example_link.text = linkExample.url
        Log.d("linkXXX", "url: ${linkExample.url}")
        holder.itemView.setOnClickListener {
            listener.onExampleClick(linkExample)
        }
    }

    class ExampleViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)
}
interface OnExampleClickListener {
    fun onExampleClick(example: LinkExample)
}