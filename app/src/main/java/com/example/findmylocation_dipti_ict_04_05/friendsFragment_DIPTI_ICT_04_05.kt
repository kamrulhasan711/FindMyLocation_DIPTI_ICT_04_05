package com.example.findmylocation_dipti_ict_04_05

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findmylocation_dipti_ict_04_05.adapter.UserAdapter_DIPTI_ICT_04_05
import com.example.findmylocation_dipti_ict_04_05.databinding.FragmentFriendsDiptiIct0405Binding
import com.example.findmylocation_dipti_ict_04_05.view.AuthenticationViewModel_DIPTI_ICT_04_05
import com.example.findmylocation_dipti_ict_04_05.viewmodel.FireStoreViewModel_DIPTI_ICT_04_05
import com.example.findmylocation_dipti_ict_04_05.viewmodel.LocationViewModel_DIPTI_ICT_04_05
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class friendsFragment_DIPTI_ICT_04_05 : Fragment() {
    private lateinit var binding: FragmentFriendsDiptiIct0405Binding
    private lateinit var firestoreViewModelDIPTIICT0405: FireStoreViewModel_DIPTI_ICT_04_05
    private lateinit var authenticationViewModelDIPTIICT0405: AuthenticationViewModel_DIPTI_ICT_04_05
    private lateinit var userAdapterDIPTIICT0405: UserAdapter_DIPTI_ICT_04_05
    private lateinit var locationViewModelDIPTIICT0405: LocationViewModel_DIPTI_ICT_04_05
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                Toast.makeText(requireContext(), "Location Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendsDiptiIct0405Binding.inflate(inflater,container, false)

        firestoreViewModelDIPTIICT0405 = ViewModelProvider(this).get(FireStoreViewModel_DIPTI_ICT_04_05::class.java)
        locationViewModelDIPTIICT0405 = ViewModelProvider(this).get(LocationViewModel_DIPTI_ICT_04_05::class.java)
        authenticationViewModelDIPTIICT0405 = ViewModelProvider(this).get(AuthenticationViewModel_DIPTI_ICT_04_05::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationViewModelDIPTIICT0405.initializeFusedLocationClient(fusedLocationClient)

        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            // Permission is already granted
            getLocation()
        }
        userAdapterDIPTIICT0405 = UserAdapter_DIPTI_ICT_04_05(emptyList())
        binding.userRV.apply {
            adapter = userAdapterDIPTIICT0405
            layoutManager = LinearLayoutManager(requireContext())
        }

        fetchUsers()

        binding.locationBtn.setOnClickListener {
            startActivity(Intent(requireContext(),MapsActivity_DIPTI_ICT_04_05::class.java))
        }


        return binding.root
    }

    private fun fetchUsers() {
        firestoreViewModelDIPTIICT0405.getAllUsers(requireContext()){
            userAdapterDIPTIICT0405.updateData(it)
        }
    }

    private fun getLocation() {
        locationViewModelDIPTIICT0405.getLastLocation {
            // Save location to Firestore for the current user
            authenticationViewModelDIPTIICT0405.getCurrentUserId().let { userId ->
                firestoreViewModelDIPTIICT0405.updateUserLocation(requireContext(),userId, it)
            }
        }
    }

}