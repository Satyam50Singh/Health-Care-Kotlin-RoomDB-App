package satya.app.healthcareapp.view.activites

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import satya.app.healthcareapp.R
import satya.app.healthcareapp.databinding.ActivitySplashBinding
import satya.app.healthcareapp.utils.Constants
import satya.app.healthcareapp.utils.PreferenceManager
import satya.app.healthcareapp.utils.Utils

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // set the theme
        val isDarkModeEnabled = PreferenceManager.getSharedPreferencesBooleanValue(this, Constants.PREF_DARK_MODE_VALUE)
        Utils.setAppTheme(this, isDarkModeEnabled)

        val sessionId = PreferenceManager.getSharedPreferences(this, Constants.PREF_SESSION_ID)
        try {
            when {
                sessionId!!.isEmpty() -> {
                    Handler().postDelayed({
                        finishAffinity()
                        Utils.switchActivity(this, LoginActivity::class.java)
                        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_in_right)
                    }, 2000)

                }
                else -> {
                    Handler().postDelayed({
                        finishAffinity()
                        Utils.switchActivity(this, DashboardActivity::class.java)
                        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_in_right)
                    }, 2000)
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}