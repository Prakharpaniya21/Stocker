package com.example.stocker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.stocker.databinding.FragmentStockDetailBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment



class StockDetailBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentStockDetailBottomSheetBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStockDetailBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val stockSymbol = arguments?.getString("symbol")
        val stockPrice = arguments?.getDouble("price")

        binding.stockNameTextView.text = stockSymbol
        binding.stockPriceTextView.text = "$${stockPrice.toString()}"
    }
}
