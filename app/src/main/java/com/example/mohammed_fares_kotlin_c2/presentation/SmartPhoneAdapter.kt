package com.example.mohammed_fares_kotlin_c2.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mohammed_fares_kotlin_c2.R
import com.example.mohammed_fares_kotlin_c2.databinding.SmartphoneItemBinding
import com.example.mohammed_fares_kotlin_c2.model.SmartPhone
import com.squareup.picasso.Picasso

class SmartPhoneAdapter(val listiner: OnSmartPhoneClicked): ListAdapter<SmartPhone, SmartPhoneAdapter.SmartPhoneViewHolder>(SmartPhoneDC()) {

    interface OnSmartPhoneClicked {
        fun onClick(smartPhone: SmartPhone)
    }

    inner class SmartPhoneViewHolder(val binding: SmartphoneItemBinding): ViewHolder(binding.root)

    class SmartPhoneDC: DiffUtil.ItemCallback<SmartPhone>() {
        override fun areItemsTheSame(oldItem: SmartPhone, newItem: SmartPhone): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SmartPhone, newItem: SmartPhone): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmartPhoneViewHolder {
        val binding = SmartphoneItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SmartPhoneViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SmartPhoneViewHolder, position: Int) {

        val smartPhone = getItem(position)
        holder.binding.smartphoneName.text = smartPhone.name
        holder.binding.smartphonePrice.text = "${smartPhone.price} £"
        Picasso.get().load(smartPhone.image).placeholder(R.drawable.phone_placeholder_ic).resize(200,200).into(holder.binding.smartphoneImage)
        if (smartPhone.fingerPrint) {
            holder.binding.smartphoneFp.setImageResource(R.drawable.fp_true)
        } else {
            holder.binding.smartphoneFp.setImageResource(R.drawable.baseline_do_not_touch_24)
        }

        holder.binding.root.setOnClickListener({
            listiner.onClick(smartPhone)
        })
    }

    fun getSmartPhone(i:Int): SmartPhone {
        return getItem(i)
    }


}