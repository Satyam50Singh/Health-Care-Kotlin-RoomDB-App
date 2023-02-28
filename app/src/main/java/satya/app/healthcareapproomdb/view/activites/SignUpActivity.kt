package satya.app.healthcareapproomdb.view.activites

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import satya.app.healthcareapproomdb.R
import satya.app.healthcareapproomdb.databinding.ActivitySignUpBinding
import satya.app.healthcareapproomdb.db.Database
import satya.app.healthcareapproomdb.utils.Utils
import satya.app.healthcareapproomdb.utils.Utils.Companion.toastMessage


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    // notification variables
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder

    companion object {
        private const val CHANNEL_NAME = "CHANNEL_1"
        private const val CHANNEL_ID = "satya.app.notifications"
        private const val NOTIFICATION_ID = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
    }

    private fun setListeners() {
        binding.btnSignUp.setOnClickListener {
            val username: String = binding.etUsername.text.toString().trim()
            val email: String = binding.etEmail.text.toString().trim()
            val password: String = binding.etPassword.text.toString().trim()
            if (username.isEmpty()) {
                toastMessage(this, getString(R.string.please_enter_username))
            } else if (email.isEmpty()) {
                toastMessage(this, getString(R.string.please_enter_email))
            } else if (!Utils.isEmailValid(email)) {
                toastMessage(this, getString(R.string.please_enter_valid_email))
            } else if (password.isEmpty()) {
                toastMessage(this, getString(R.string.please_enter_password))
            } else if (password.length <=7) {
                toastMessage(this, getString(R.string.validate_password_length))
            } else {
                doSignUp(username, email, password)
            }
        }
        binding.tvUserLogin.setOnClickListener {
            Utils.switchActivity(this, LoginActivity::class.java)
        }
    }

    private fun doSignUp(username: String, email: String, password: String) {
        val db = Database(applicationContext, "healthcare", null, 1)
        if (!db.checkUser(email)) {
            val registerUser = db.registerUser(username, email, password)
            if (registerUser) {
                launchNotification()
                toastMessage(this, getString(R.string.user_registered_successfully))
                Utils.switchActivity(this, LoginActivity::class.java)
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_in_right)
                finishAffinity()
            } else {
                toastMessage(this, getString(R.string.something_wrong_happened))
            }
        } else {
            toastMessage(this, getString(R.string.user_already_exist))
        }
    }

    private fun launchNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = CHANNEL_NAME
            val description = getString(R.string.user_registration)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationChannel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        builder = Notification.Builder(this, Companion.CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_person)
            .setContentTitle(getString(R.string.user_registration))
            .setContentText(getString(R.string.user_registered_successfully_notification))
            .setAutoCancel(true)

        val intent =
            Intent(this, SplashActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        builder.setContentIntent(pendingIntent)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}