package com.example.findmylocation_dipti_ict_04_05

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.findmylocation_dipti_ict_04_05.databinding.FragmentProfileDiptiIct0405Binding
import com.example.findmylocation_dipti_ict_04_05.view.AuthenticationViewModel_DIPTI_ICT_04_05
import com.example.findmylocation_dipti_ict_04_05.viewmodel.FireStoreViewModel_DIPTI_ICT_04_05
import com.example.findmylocation_dipti_ict_04_05.viewmodel.LocationViewModel_DIPTI_ICT_04_05
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment_DIPTI_ICT_04_05 : Fragment() {

    private lateinit var binding: FragmentProfileDiptiIct0405Binding
    private lateinit var authViewModel: AuthenticationViewModel_DIPTI_ICT_04_05
    private lateinit var firestoreViewModelDIPTIICT0405: FireStoreViewModel_DIPTI_ICT_04_05
    private lateinit var locationViewModelDIPTIICT0405: LocationViewModel_DIPTI_ICT_04_05
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileDiptiIct0405Binding.inflate(inflater, container, false)

        authViewModel = ViewModelProvider(this).get(AuthenticationViewModel_DIPTI_ICT_04_05::class.java)
        firestoreViewModelDIPTIICT0405 = ViewModelProvider(this).get(FireStoreViewModel_DIPTI_ICT_04_05::class.java)
        locationViewModelDIPTIICT0405 = ViewModelProvider(this).get(LocationViewModel_DIPTI_ICT_04_05::class.java)


        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), LoginActivity_DIPTI_ICT_04_05::class.java))

        }

        binding.homeBtn.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity_DIPTI_ICT_04_05::class.java))
        }

        loadUserInfo()
        binding.updateBtn.setOnClickListener {
            val newName = binding.NameEt.text.toString()
            val newLocation = binding.locationEt.text.toString()

            updateBtn(newName, newLocation)
        }

        return binding.root
    }

    private fun updateBtn(newName: String, newLocation: String) {
        val currentUser = authViewModel.getCurrentUser()
        if (currentUser != null) {
            val userId = currentUser.uid
            firestoreViewModelDIPTIICT0405.updateUser(requireContext(), userId, newName, newLocation)
            Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), MainActivity_DIPTI_ICT_04_05::class.java))
        } else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadUserInfo() {
        val currentUser = authViewModel.getCurrentUser()
        if(currentUser != null) {
            binding.emailEt.setText(currentUser.email)
            firestoreViewModelDIPTIICT0405.getUser(requireContext(), currentUser.uid){ user ->
                if (currentUser.displayName != null) {
                    binding.NameEt.setText(currentUser.displayName)
                }
            }
        }else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }

    }
}