package com.example.findmylocation_dipti_ict_04_05

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.findmylocation_dipti_ict_04_05.databinding.ActivityRegisterDiptiIct0405Binding
import com.example.findmylocation_dipti_ict_04_05.view.AuthenticationViewModel_DIPTI_ICT_04_05
import com.example.findmylocation_dipti_ict_04_05.viewmodel.FireStoreViewModel_DIPTI_ICT_04_05
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class RegisterActivity_DIPTI_ICT_04_05 : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterDiptiIct0405Binding
    private lateinit var authenticationViewModelDIPTIICT0405: AuthenticationViewModel_DIPTI_ICT_04_05
    private lateinit var firestoreViewModelDIPTIICT0405: FireStoreViewModel_DIPTI_ICT_04_05
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterDiptiIct0405Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        authenticationViewModelDIPTIICT0405 = ViewModelProvider(this).get(AuthenticationViewModel_DIPTI_ICT_04_05::class.java)
        firestoreViewModelDIPTIICT0405 = ViewModelProvider(this).get(FireStoreViewModel_DIPTI_ICT_04_05::class.java)

        binding.registerBtn.setOnClickListener {
            val name = binding.displayNameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            val confirmPassword = binding.conPasswordEt.text.toString()
            val location = "Don't found any location yet"
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT)
                    .show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            } else {
                authenticationViewModelDIPTIICT0405.register(email, password, {
                    firestoreViewModelDIPTIICT0405.saveUser(this, authenticationViewModelDIPTIICT0405.getCurrentUserId(), name, email, location)
                    startActivity(Intent(this, MainActivity_DIPTI_ICT_04_05::class.java))
                    finish()
                }, {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                })
            }
        }
        binding.loginTxt.setOnClickListener {
            startActivity(Intent(this, LoginActivity_DIPTI_ICT_04_05::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser != null) {
            startActivity(Intent(this@RegisterActivity_DIPTI_ICT_04_05, MainActivity_DIPTI_ICT_04_05::class.java))
            finish()
        }
    }
}
