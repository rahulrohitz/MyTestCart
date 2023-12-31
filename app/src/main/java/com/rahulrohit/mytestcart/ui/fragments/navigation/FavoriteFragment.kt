package com.rahulrohit.mytestcart.ui.fragments.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahulrohit.mytestcart.adapter.FavoriteAdapter
import com.rahulrohit.mytestcart.database.room.favorite.SharedViewModel
import com.rahulrohit.mytestcart.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null


    private val binding get() = _binding!!
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        favoriteAdapter = FavoriteAdapter()
        binding.recyclerViewFavProduct.adapter = favoriteAdapter
        binding.recyclerViewFavProduct.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        sharedViewModel.getAllProducts().observe(viewLifecycleOwner) { products ->

            favoriteAdapter.setProducts(products)
        }

        favoriteAdapter.setOnDeleteClickListener {productEntity ->

            sharedViewModel.deleteProduct(productEntity.id)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
