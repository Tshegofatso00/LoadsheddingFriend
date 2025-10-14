package za.co.tshegojabu.loadsheddingfriend.ui.theme

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import za.co.tshegojabu.loadsheddingfriend.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import za.co.tshegojabu.loadsheddingfriend.models.UserProfile

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    companion object {
        private const val TAG = "RegisterActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        val inputFullName = findViewById<TextInputEditText>(R.id.inputFullName)
        val inputEmail = findViewById<TextInputEditText>(R.id.inputEmail)
        val inputPassword = findViewById<TextInputEditText>(R.id.inputPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvGoToLogin = findViewById<TextView>(R.id.tvGoToLogin)

        btnRegister.setOnClickListener {
            val fullName = inputFullName.text?.toString()?.trim() ?: ""
            val email = inputEmail.text?.toString()?.trim() ?: ""
            val password = inputPassword.text?.toString()?.trim() ?: ""

            if (fullName.isEmpty()) {
                inputFullName.error = "Please enter your full name"
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                inputEmail.error = "Please enter a valid email"
                return@setOnClickListener
            }
            if (password.length < 6) {
                inputPassword.error = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            registerUser(fullName, email, password)
        }

        tvGoToLogin.setOnClickListener {
            Toast.makeText(this, "Login screen coming soon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUser(fullName: String, email: String, password: String) {
        Toast.makeText(this, "Registering user...", Toast.LENGTH_SHORT).show()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    val uid = firebaseUser?.uid ?: ""
                    Log.d(TAG, "Registration successful: $uid")

                    val userProfile = UserProfile(
                        uid = uid,
                        fullName = fullName,
                        email = email,
                        createdAt = System.currentTimeMillis()
                    )

                    val dbRef = Firebase.database.reference.child("users").child(uid)
                    dbRef.setValue(userProfile)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Profile saved successfully!", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "Profile saved in database for uid=$uid")
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to save profile: ${e.message}", Toast.LENGTH_LONG).show()
                            Log.e(TAG, "Database error: ${e.message}")
                        }
                } else {
                    val message = task.exception?.localizedMessage ?: "Unknown error"
                    Toast.makeText(this, "Registration failed: $message", Toast.LENGTH_LONG).show()
                    Log.e(TAG, "Registration failed: $message")
                }
            }
    }
}
