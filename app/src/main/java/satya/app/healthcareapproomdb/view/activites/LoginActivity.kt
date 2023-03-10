package satya.app.healthcareapproomdb.view.activites

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import satya.app.healthcareapproomdb.R
import satya.app.healthcareapproomdb.databinding.ActivityLoginBinding
import satya.app.healthcareapproomdb.utils.Constants.PREF_EMAIL
import satya.app.healthcareapproomdb.utils.Constants.PREF_PASSWORD
import satya.app.healthcareapproomdb.utils.Constants.PREF_REMEMBER_ME
import satya.app.healthcareapproomdb.utils.Constants.PREF_SESSION_ID
import satya.app.healthcareapproomdb.utils.Constants.PREF_USERNAME
import satya.app.healthcareapproomdb.utils.Constants.PREF_USER_ID
import satya.app.healthcareapproomdb.utils.Constants.sessionId
import satya.app.healthcareapproomdb.utils.PreferenceManager
import satya.app.healthcareapproomdb.utils.PreferenceManager.setSharedPreferences
import satya.app.healthcareapproomdb.utils.Utils
import satya.app.healthcareapproomdb.utils.Utils.switchActivity
import satya.app.healthcareapproomdb.utils.Utils.toastMessage
import satya.app.healthcareapproomdb.viewmodels.UserAuthViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: UserAuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCredentials()

        setListeners();

    }

    // method to fetch the credentials from  shared preference
    private fun setCredentials() {
        if (PreferenceManager.getSharedPreferencesBooleanValue(this, PREF_REMEMBER_ME)) {
            binding.etUserEmail.setText(PreferenceManager.getSharedPreferences(this, PREF_EMAIL))
            binding.etPassword.setText(PreferenceManager.getSharedPreferences(this, PREF_PASSWORD))
            binding.cbRememberMe.isChecked = true
        }
    }

    // user control listeners
    private fun setListeners() {
        binding.btnLogin.setOnClickListener {
            val email: String = binding.etUserEmail.text.toString().trim()
            val password: String = binding.etPassword.text.toString().trim()
            if (email.isEmpty()) {
                toastMessage(this, getString(R.string.please_enter_username))
            } else if (!Utils.isEmailValid(email)) {
                toastMessage(this, getString(R.string.please_enter_valid_email))
            } else if (password.isEmpty()) {
                toastMessage(this, getString(R.string.please_enter_password))
            } else {
                doLogin(email, password)
            }
        }
        binding.tvForgotPassword.setOnClickListener {
            toastMessage(this, "Forgot Button Clicked!")
        }
        binding.tvUserRegister.setOnClickListener {
            switchActivity(this, SignUpActivity::class.java)
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_in_right)
        }
    }

    // user login code
    private fun doLogin(email: String, password: String) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.checkUserExistence(email).observe(this) { data ->
            if (data != null) {
                viewModel.userLogIn(email, password).observe(this) { result ->
                    run {
                        if (result != null) {
                            binding.progressBar.visibility = View.GONE

                            setSharedPreferences(this, PREF_USER_ID, result.userId ?: 0)
                            setSharedPreferences(this, PREF_EMAIL, email)
                            setSharedPreferences(this, PREF_PASSWORD, password)
                            setSharedPreferences(this, PREF_USERNAME, result.username)
                            setSharedPreferences(this, PREF_SESSION_ID, sessionId)
                            setSharedPreferences(
                                this, PREF_REMEMBER_ME, binding.cbRememberMe.isChecked
                            )

                            toastMessage(this, getString(R.string.logged_in_successfully))
                            switchActivity(this, DashboardActivity::class.java)
                            overridePendingTransition(
                                R.anim.enter_from_left, R.anim.exit_in_right
                            )
                            finishAffinity()
                        } else {
                            binding.progressBar.visibility = View.GONE
                            toastMessage(this, getString(R.string.invalid_credentials))
                        }
                    }
                }
            } else {
                toastMessage(this, getString(R.string.user_does_not_exit))
                binding.progressBar.visibility = View.GONE
            }
        }
        Log.e(TAG, "doLogin: email :$email and password : $password")
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}