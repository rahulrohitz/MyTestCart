package com.rahulrohit.mytestcart.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.rahulrohit.mytestcart.R
import com.rahulrohit.mytestcart.adapter.HomeProductAdapter
import com.rahulrohit.mytestcart.databinding.FragmentSeeAllBinding
import com.rahulrohit.mytestcart.api.ViewModel

class SeeAllProductFragment : Fragment() {

    private var _binding: FragmentSeeAllBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()
    private lateinit var allHomeProductAdapter: HomeProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeeAllBinding.inflate(inflater, container, false)

        allHomeProductAdapter = HomeProductAdapter()

        binding.recyclerViewAllProduct.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL,false)
        binding.recyclerViewAllProduct.adapter = allHomeProductAdapter

        binding.shimmerEffect.visibility = View.VISIBLE
        viewModel.allProducts.observe(viewLifecycleOwner) { products ->
            products?.let {
                binding.shimmerEffect.visibility = View.GONE

                allHomeProductAdapter.setProducts(it)
            }
        }

        viewModel.fetchAllProducts()

        allHomeProductAdapter.setOnItemClickListener { product ->
            val bundle = Bundle()
            bundle.putInt("id",product.id)
            ProductDetailFragment().arguments = bundle
            findNavController().navigate(R.id.HomeToDetailProducts,bundle)
        }
        return  binding.root
    }
}