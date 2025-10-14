package za.co.tshegojabu.loadsheddingfriend.ui.theme

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import za.co.tshegojabu.loadsheddingfriend.R
import java.util.Locale

class SettingsActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private val PREFS_NAME = "app_prefs"
    private val KEY_NOTIFICATIONS = "notifications_enabled"
    private val KEY_LANGUAGE = "app_language"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load saved language before setting layout
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val savedLang = prefs.getString(KEY_LANGUAGE, "en") ?: "en"
        applyLocale(savedLang)

        // Set layout
        setContentView(R.layout.activity_settings)

        // Link views from XML
        val switchNotifications = findViewById<Switch>(R.id.switch_notifications)
        val btnChangeLanguage = findViewById<Button>(R.id.btnChangeLanguage)
        val tvCurrentLanguage = findViewById<TextView>(R.id.tvCurrentLanguage)
        val btnClearData = findViewById<Button>(R.id.btnClearData)
        val btnClose = findViewById<Button>(R.id.btnClose)

        // Restore saved notification setting
        val notifEnabled = prefs.getBoolean(KEY_NOTIFICATIONS, true)
        switchNotifications.isChecked = notifEnabled
        updateLanguageLabel(tvCurrentLanguage, savedLang)

        // Notification toggle
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean(KEY_NOTIFICATIONS, isChecked).apply()
            Toast.makeText(
                this,
                if (isChecked) getString(R.string.notifications_enabled_toast)
                else getString(R.string.notifications_disabled_toast),
                Toast.LENGTH_SHORT
            ).show()
        }

        // Language switch button
        btnChangeLanguage.setOnClickListener {
            val current = prefs.getString(KEY_LANGUAGE, "en") ?: "en"
            val newLang = if (current == "en") "zu" else "en"
            prefs.edit().putString(KEY_LANGUAGE, newLang).apply()
            applyLocale(newLang)
            updateLanguageLabel(tvCurrentLanguage, newLang)
            Toast.makeText(this, getString(R.string.language_changed_to, newLang), Toast.LENGTH_SHORT).show()
            recreate()
        }

        // Clear preferences button
        btnClearData.setOnClickListener {
            prefs.edit().clear().apply()
            Toast.makeText(this, getString(R.string.data_cleared_toast), Toast.LENGTH_SHORT).show()
        }

        // Close button
        btnClose.setOnClickListener {
            finish()
        }
    }

    private fun updateLanguageLabel(tv: TextView, langCode: String) {
        val label = when (langCode) {
            "zu" -> getString(R.string.current_language_zu)
            else -> getString(R.string.current_language_en)
        }
        tv.text = label
    }

    private fun applyLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
