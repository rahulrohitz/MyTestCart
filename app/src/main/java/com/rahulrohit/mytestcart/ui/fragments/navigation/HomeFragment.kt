package com.rahulrohit.mytestcart.ui.fragments.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahulrohit.mytestcart.R
import com.rahulrohit.mytestcart.adapter.HomeProductAdapter
import com.rahulrohit.mytestcart.adapter.ProductAdapter
import com.rahulrohit.mytestcart.api.ProductResponse
import com.rahulrohit.mytestcart.databinding.FragmentHomeBinding
import com.rahulrohit.mytestcart.ui.fragments.ProductDetailFragment
import com.rahulrohit.mytestcart.api.ViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel: ViewModel by viewModels()
    private lateinit var menHomeProductAdapter: ProductAdapter
    private lateinit var womenHomeProductAdapter: ProductAdapter
    private lateinit var allHomeProductAdapter: HomeProductAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        womenHomeProductAdapter = ProductAdapter()
        menHomeProductAdapter = ProductAdapter()
        allHomeProductAdapter = HomeProductAdapter()

        binding.recyclerVieWomenProduct.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerVieWomenProduct.adapter = womenHomeProductAdapter

        binding.recyclerViewMenProduct.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewMenProduct.adapter = menHomeProductAdapter

        binding.recyclerViewAllProduct.layoutManager = GridLayoutManager(requireContext(), 2,GridLayoutManager.VERTICAL,false)
        binding.recyclerViewAllProduct.adapter = allHomeProductAdapter

        binding.shimmerEffect.visibility = View.VISIBLE
        viewModel.womenProducts.observe(viewLifecycleOwner) { products ->
            products?.let {

                binding.shimmerEffect.visibility  = View.GONE
                womenHomeProductAdapter.setProducts(it)
            }
        }

        viewModel.fetchWomenProducts()

        viewModel.menProducts.observe(viewLifecycleOwner) { products ->
            products?.let {
                menHomeProductAdapter.setProducts(it)

            }
        }

        viewModel.fetchMenProducts()

        viewModel.allProducts.observe(viewLifecycleOwner) { products ->
            products?.let {
                allHomeProductAdapter.setProducts(it)

            }
        }

        viewModel.fetchAllProducts()


        allHomeProductAdapter.setOnItemClickListener { product ->

            navigateProductToDetail(product)

        }

        menHomeProductAdapter.setOnItemClickListener { product ->

            navigateProductToDetail(product)
        }
        womenHomeProductAdapter.setOnItemClickListener { product ->

            navigateProductToDetail(product)
        }

        binding.tvWomenSellAll.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("women","women")
            ProductDetailFragment().arguments = bundle
            findNavController().navigate(R.id.HomeToSeeAllProducts,bundle)
        }

        binding.tvMenAll.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("men","men")
            ProductDetailFragment().arguments = bundle
            findNavController().navigate(R.id.HomeToSeeAllProducts,bundle)
        }

        binding.tvSellAll.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("all","all")
            ProductDetailFragment().arguments = bundle
            findNavController().navigate(R.id.HomeToSeeAllProducts,bundle)
        }
        return binding.root
    }

    private fun navigateProductToDetail(product:ProductResponse){
        val bundle = Bundle()
        bundle.putInt("id",product.id)
        ProductDetailFragment().arguments = bundle
        findNavController().navigate(R.id.HomeToDetailProducts,bundle)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}