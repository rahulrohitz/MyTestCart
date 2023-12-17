package com.rahulrohit.mytestcart.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.rahulrohit.mytestcart.R
import com.rahulrohit.mytestcart.api.ViewModel
import com.rahulrohit.mytestcart.database.room.favorite.ProductDatabase
import com.rahulrohit.mytestcart.database.room.favorite.ProductEntity
import com.rahulrohit.mytestcart.databinding.FragmentProductDetailBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private var isExpanded = false
    private var isFavorite = false
    private val viewModel: ViewModel by viewModels()
    // Inside your Fragment class
    private val db: ProductDatabase by lazy {
        ProductDatabase.getDatabase(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =  FragmentProductDetailBinding.inflate(inflater, container, false)

        val id = arguments?.getInt("id",0)?: 0

        viewModel.fetchProduct(id)

        viewModel.products.observe(viewLifecycleOwner) { product ->
            binding.productTitle.text = product.title
            binding.productPrice.text = product.price.toString()
            Glide.with(requireContext()).load(product.image).into(binding.productImageView)
            binding.productDescripton.text = product.description
            binding.productCategory.text = product.category
            binding.productRate.text = product.rating.rate.toString()


            binding.backButton.setOnClickListener {
                requireActivity().onBackPressed()
            }

            binding.addToCartButton.setOnClickListener {
                GlobalScope.launch {
                    val cartEntity = ProductEntity(
                        id = product.id,
                        title = product.title,
                        description = product.description,
                        price = product.price,
                        category =  product.category,
                        image =  product.image,
                        rate = product.rating.rate
                    )
                    db.productDao().insertProduct(cartEntity)
                }
            }
            binding.favoriteIcon.setOnClickListener {
                isFavorite = !isFavorite
                updateFavoriteImageView()

                GlobalScope.launch {
                val favEntity = ProductEntity(
                    id = product.id,
                    title = product.title,
                    description = product.description,
                    price = product.price,
                    category =  product.category,
                    image =  product.image,
                    rate = product.rating.rate,
                     isFavorite =  isFavorite
                )
                db.productDao().insertProduct(favEntity)
                }
            }
        }
        toggleTextView()

        binding.productDescripton.setOnClickListener {
            isExpanded = !isExpanded
            toggleTextView()
        }

        return  binding.root
    }

    private fun updateFavoriteImageView() {
        val drawableRes = if (isFavorite) R.drawable.ic_favorite_24 else R.drawable.ic_unfavorite_24
        binding.favoriteIcon.setImageResource(drawableRes)
    }
    private fun toggleTextView() {
        if (isExpanded) {
            binding.productDescripton.maxLines = Integer.MAX_VALUE
            binding.productDescripton.ellipsize = null
        } else {
            binding.productDescripton.maxLines = 1
            binding.productDescripton.ellipsize = TextUtils.TruncateAt.END
        }
    }
}