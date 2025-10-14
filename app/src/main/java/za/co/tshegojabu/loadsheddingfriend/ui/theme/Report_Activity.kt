package za.co.tshegojabu.loadsheddingfriend.ui.theme

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import za.co.tshegojabu.loadsheddingfriend.R

class ReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val inputReport = findViewById<EditText>(R.id.inputReport)
        val btnSubmit = findViewById<Button>(R.id.btnSubmitReport)

        // Firebase Realtime Database reference for /reports
        val db = FirebaseDatabase.getInstance().reference.child("reports")

        btnSubmit.setOnClickListener {
            val reportText = inputReport.text.toString().trim()
            if(reportText.isNotEmpty()){
                // Push new report
                val newReport = db.push()
                newReport.setValue(mapOf("report" to reportText))
                    .addOnSuccessListener {
                        Toast.makeText(this, "Report submitted!", Toast.LENGTH_SHORT).show()
                        inputReport.text.clear()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(this, "Please write a report", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
