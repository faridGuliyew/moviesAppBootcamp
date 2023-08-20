package com.example.moviesappbootcamp.presentation.adapter.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappbootcamp.base.diffUtil.DiffCallbackBase
import com.example.moviesappbootcamp.common.model.other.PreferencesModel
import com.example.moviesappbootcamp.databinding.ItemPaymentMethodBinding

class PaymentMethodRvAdapter : RecyclerView.Adapter<PaymentMethodRvAdapter.PaymentMethodViewHolder>() {
    inner class PaymentMethodViewHolder (private val itemPaymentMethodBinding: ItemPaymentMethodBinding) : RecyclerView.ViewHolder(itemPaymentMethodBinding.root){
        fun bind(cardModel : PreferencesModel){
            with(itemPaymentMethodBinding){
                card = cardModel
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodViewHolder {
        return PaymentMethodViewHolder(
            ItemPaymentMethodBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: PaymentMethodViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    val differ = AsyncListDiffer(this, DiffCallbackBase<PreferencesModel>())
}