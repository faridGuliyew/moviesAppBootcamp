package com.example.moviesappbootcamp.presentation.mainScreens.paymentFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.example.moviesappbootcamp.R
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.common.model.other.PreferencesModel
import com.example.moviesappbootcamp.databinding.FragmentPaymentBinding
import com.example.moviesappbootcamp.presentation.adapter.rv.PaymentMethodRvAdapter

class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate) {

    private val cardAdapter = PaymentMethodRvAdapter()

    override fun onViewCreatedLight() {

        setRv()
        goToAddCard()
    }

    private fun setRv(){
        binding.cardsRv.adapter = cardAdapter
        setCards()
    }

    private fun setCards(){
        val cards = arrayListOf<PreferencesModel>()
        cards.add(PreferencesModel(ResourcesCompat.getDrawable(resources, R.drawable.card_paypal,context?.theme),"PayPal"))
        cardAdapter.differ.submitList(cards)
    }

    private fun goToAddCard(){
        binding.buttonAddCard.setOnClickListener {
            findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToAddCardFragment())
        }
    }

}