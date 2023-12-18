package com.rahulrohit.mytestcart.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rahulrohit.mytestcart.R
import com.rahulrohit.mytestcart.database.room.user.UserDao
import com.rahulrohit.mytestcart.database.room.user.UserDatabase
import com.rahulrohit.mytestcart.databinding.FragmentSigninBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SigninFragment : Fragment() {

    private lateinit var binding: FragmentSigninBinding
    private lateinit var userDao: UserDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =   FragmentSigninBinding.inflate(inflater, container, false)

        userDao = UserDatabase.getDatabase(requireContext()).userDao()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassWord.text.toString()

            GlobalScope.launch {
                val user = userDao.getUser(email, password)
                requireActivity().runOnUiThread {
                    if (user != null) {

                        Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                        findNavController().navigate(R.id.navigation_home)
                    } else {
                        Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.tvSignUp.setOnClickListener{
            findNavController().navigate(R.id.signupFragment)
        }
        return binding.root
    }
}