package com.example.wash_i

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {
    private lateinit var phoneEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        phoneEditText = findViewById(R.id.phoneNumber)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.continueButton)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("users")

        loginButton.setOnClickListener {
            val phone = phoneEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter phone number and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authenticateWithPhoneAndPassword(phone, password)
        }
    }

    private fun authenticateWithPhoneAndPassword(phone: String, password: String) {
        database.orderByChild("user/phone").equalTo(phone)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {
                            val user = userSnapshot.child("user")
                            val storedPassword = user.child("password").getValue(String::class.java)
                            if (storedPassword == password) {
                                val userId = userSnapshot.key
                                if (userId != null) {
                                    checkUserType(userId)
                                }
                            } else {
                                Toast.makeText(this@LoginActivity, "Invalid password", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("LoginActivity", "Database error: ${error.message}")
                }
            })
    }

    private fun checkUserType(userId: String) {
        database.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userType = snapshot.child("user/userType").value as? String
                    Log.d("LoginActivity", "userType: $userType")
                    if (userType != null) {
                        when (userType) {
                            "ServiceProvider" -> navigateToServiceProvider()
                            "Customer" -> navigateToCustomer()
                            else -> {
                                Toast.makeText(this@LoginActivity, "Unknown user type", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "User type is null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "User not found in database", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("LoginActivity", "Database error: ${error.message}")
            }
        })
    }

    private fun navigateToServiceProvider() {
        val intent = Intent(this, ServiceProviderActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToCustomer() {
        val intent = Intent(this, CustomerActivity::class.java)
        startActivity(intent)
        finish()
    }
}
