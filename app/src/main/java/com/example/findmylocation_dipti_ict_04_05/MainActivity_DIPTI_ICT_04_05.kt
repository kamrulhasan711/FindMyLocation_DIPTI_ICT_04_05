package com.example.findmylocation_dipti_ict_04_05

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.findmylocation_dipti_ict_04_05.databinding.ActivityMainDiptiIct0405Binding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity_DIPTI_ICT_04_05 : AppCompatActivity() {

    private lateinit var binding: ActivityMainDiptiIct0405Binding
    private lateinit var actionDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainDiptiIct0405Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.fragmentContainerView)

        // Setup bottom navigation
        binding.bottomBar.setupWithNavController(navController)

        // Setup drawer navigation
        binding.drawerNav.setupWithNavController(navController)

        // Setup ActionBarDrawerToggle
        actionDrawerToggle = ActionBarDrawerToggle(
            this, binding.drawerLayout,
            R.string.nav_open,
            R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(actionDrawerToggle)
        actionDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Handle drawer item clicks
        binding.drawerNav.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout -> {
                    logout()
                    true
                }
                R.id.profile -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                R.id.friends -> {
                    navController.navigate(R.id.friendsFragment)
                    true
                }
                else -> false
            }
        }

        // Handle bottom navigation item clicks
        binding.bottomBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout -> {
                    logout()
                    true
                }
                R.id.friends -> {
                    navController.navigate(R.id.friendsFragment)
                    true
                }
                R.id.profile -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun logout() {
        Firebase.auth.signOut()
        startActivity(Intent(this, LoginActivity_DIPTI_ICT_04_05::class.java))
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}
