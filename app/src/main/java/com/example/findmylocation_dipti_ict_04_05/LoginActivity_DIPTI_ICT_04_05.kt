package com.example.findmylocation_dipti_ict_04_05

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.findmylocation_dipti_ict_04_05.databinding.ActivityLoginDiptiIct0405Binding
import com.example.findmylocation_dipti_ict_04_05.view.AuthenticationViewModel_DIPTI_ICT_04_05
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity_DIPTI_ICT_04_05 : AppCompatActivity() {
    private lateinit var binding: ActivityLoginDiptiIct0405Binding
    private lateinit var authenticationViewModelDIPTIICT0405: AuthenticationViewModel_DIPTI_ICT_04_05
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginDiptiIct0405Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        authenticationViewModelDIPTIICT0405 = ViewModelProvider(this).get(AuthenticationViewModel_DIPTI_ICT_04_05::class.java)

        binding.loginBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(this, "Please enter valid password", Toast.LENGTH_SHORT).show()
            } else {
                authenticationViewModelDIPTIICT0405.login(email, password, {
                    startActivity(Intent(this, MainActivity_DIPTI_ICT_04_05::class.java))
                    finish()
                }, {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                })
            }
        }

        binding.registerTxt.setOnClickListener {
            startActivity(Intent(this, RegisterActivity_DIPTI_ICT_04_05::class.java))
        }

        binding.forgotpass.setOnClickListener {
            Toast.makeText(this, "Forgot password", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser != null) {
            startActivity(Intent(this@LoginActivity_DIPTI_ICT_04_05, MainActivity_DIPTI_ICT_04_05::class.java))
            finish()
        }
    }
}