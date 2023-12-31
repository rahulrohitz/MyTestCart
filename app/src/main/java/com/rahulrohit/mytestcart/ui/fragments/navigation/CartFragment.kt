package com.rahulrohit.mytestcart.ui.fragments.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahulrohit.mytestcart.adapter.CartAdapter
import com.rahulrohit.mytestcart.adapter.FavoriteAdapter
import com.rahulrohit.mytestcart.database.room.favorite.SharedViewModel
import com.rahulrohit.mytestcart.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private lateinit var favoriteAdapter: CartAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        favoriteAdapter = CartAdapter()
        binding.recyclerViewCartProduct.adapter = favoriteAdapter
        binding.recyclerViewCartProduct.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        sharedViewModel.getAllCartProducts().observe(viewLifecycleOwner) { products ->

            favoriteAdapter.setProducts(products)
        }

        favoriteAdapter.setOnDeleteClickListener {productEntity ->

            sharedViewModel.deletecartProduct(productEntity.id)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}