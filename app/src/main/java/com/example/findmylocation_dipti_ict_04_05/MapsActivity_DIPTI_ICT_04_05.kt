package com.example.findmylocation_dipti_ict_04_05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.findmylocation_dipti_ict_04_05.databinding.ActivityMapsDiptiIct0405Binding
import com.example.findmylocation_dipti_ict_04_05.viewmodel.FireStoreViewModel_DIPTI_ICT_04_05

class MapsActivity_DIPTI_ICT_04_05 : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsDiptiIct0405Binding
    private lateinit var firestoreViewModelDIPTIICT0405: FireStoreViewModel_DIPTI_ICT_04_05

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsDiptiIct0405Binding.inflate(layoutInflater)
        setContentView(binding.root)

        firestoreViewModelDIPTIICT0405 = ViewModelProvider(this).get(FireStoreViewModel_DIPTI_ICT_04_05::class.java)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnZoomIn.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.zoomIn())
        }
        binding.btnZoomOut.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.zoomOut())
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        firestoreViewModelDIPTIICT0405.getAllUsers(this) { users ->
            var lastLatLng: LatLng? = null

            for (user in users) {
                val userLocation = user.location
                val latLng: LatLng

                if (userLocation.isEmpty() || userLocation == "Don't found any location yet" || userLocation == "Location not available") {
                    latLng = LatLng(37.4220936, -122.083922) // Default location
                } else {
                    latLng = parseLocation(userLocation)
                    val markerOptions = MarkerOptions().position(latLng).title(user.displayname)
                    googleMap.addMarker(markerOptions)
                }

                lastLatLng = latLng // Update lastLatLng
            }

            // Move the camera to the last user's location
            lastLatLng?.let {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
            }
        }
    }

    private fun parseLocation(location: String): LatLng {
        return try {
            val latLngSplit = location.split(", ")
            val latitude = latLngSplit[0].substringAfter("Lat: ").toDouble()
            val longitude = latLngSplit[1].substringAfter("Long: ").toDouble()
            LatLng(latitude, longitude)
        } catch (e: Exception) {
            LatLng(37.4220936, -122.083922) // Fallback to default location in case of error
        }
    }
}
