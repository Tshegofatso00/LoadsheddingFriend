package za.co.tshegojabu.loadsheddingfriend.ui.theme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import za.co.tshegojabu.loadsheddingfriend.R
import za.co.tshegojabu.loadsheddingfriend.ui.theme.ReportActivity
import za.co.tshegojabu.loadsheddingfriend.ui.theme.SettingsActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val inputEmail = findViewById<TextInputEditText>(R.id.inputEmail)
        val inputPassword = findViewById<TextInputEditText>(R.id.inputPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvGoToRegister = findViewById<TextView>(R.id.tvGoToRegister)

        // New buttons
        val btnSettings = findViewById<Button>(R.id.btnSettings)
        val btnReport = findViewById<Button>(R.id.btnReport)

        // --- LOGIN ---
        btnLogin.setOnClickListener {
            val email = inputEmail.text?.toString()?.trim() ?: ""
            val password = inputPassword.text?.toString()?.trim() ?: ""

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                inputEmail.error = "Enter a valid email"
                return@setOnClickListener
            }
            if (password.length < 6) {
                inputPassword.error = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            loginUser(email, password)
        }

        // --- REGISTER ---
        tvGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // --- SETTINGS ---
        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // --- COMMUNITY REPORT ---
        btnReport.setOnClickListener {
            val intent = Intent(this, ReportActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Login successful")
                    Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show()
                    // Stay on this screen for demo to access Settings/Report buttons
                } else {
                    val message = task.exception?.localizedMessage ?: "Unknown error"
                    Log.e(TAG, "Login failed: $message")
                    Toast.makeText(this, "Login failed: $message", Toast.LENGTH_LONG).show()
                }
            }
    }
}


