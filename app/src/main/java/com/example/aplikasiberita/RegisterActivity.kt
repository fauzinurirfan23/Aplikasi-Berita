package com.example.aplikasiberita

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {

    lateinit var etEmail: EditText
    lateinit var etPass: EditText
    lateinit var btnRegister: Button
    lateinit var tvLogin: TextView

    private lateinit var authFirebase: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // ✅ INIT FIREBASE AUTH
        authFirebase = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.et_email)
        etPass = findViewById(R.id.et_password)
        btnRegister = findViewById(R.id.btn_register_id)
        tvLogin = findViewById(R.id.tv_login)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPass.text.toString().trim()

            when {
                email.isEmpty() -> {
                    etEmail.error = "Email tidak boleh kosong"
                    etEmail.requestFocus()
                }
                password.isEmpty() -> {
                    etPass.error = "Password tidak boleh kosong"
                    etPass.requestFocus()
                }
                password.length < 6 -> {
                    etPass.error = "Password minimal 6 karakter"
                    etPass.requestFocus()
                }
                else -> {
                    register(email, password)
                }
            }
        }
    }

    private fun register(email: String, password: String) {
        authFirebase.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Register berhasil", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, task.exception?.message ?: "Register gagal", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
