package com.rahulrohit.mytestcart.ui.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rahulrohit.mytestcart.R
import com.rahulrohit.mytestcart.database.room.user.User
import com.rahulrohit.mytestcart.database.room.user.UserDao
import com.rahulrohit.mytestcart.database.room.user.UserDatabase
import com.rahulrohit.mytestcart.databinding.FragmentSignupBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
        private lateinit var userDao: UserDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =   FragmentSignupBinding.inflate(inflater, container, false)

        userDao = UserDatabase.getDatabase(requireContext()).userDao()

        binding.btnSignup.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassWord.text.toString()
            val confirmPassword = binding.etCPassWord.text.toString()


            if (name.isNotEmpty() && isPasswordValid(password) && confirmPassword.isNotEmpty()) {

                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (password == confirmPassword) {

                        val user = User(name = name, email = email, password = password)
                        GlobalScope.launch {
                            userDao.insert(user)
                            requireActivity().runOnUiThread {
                                Toast.makeText(
                                    requireContext(), "User created successfully", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.signinFragment)
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    binding.emailLayout.error = "invalid email address"
                }
            } else {
                Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            }
        }
        return  binding.root
    }
    private fun isPasswordValid(password: String): Boolean {
        val passwordRegex = Regex("(?=.*[A-Z])(?=.*\\d).{8,}")
        return passwordRegex.matches(password)
    }
}