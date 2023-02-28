package satya.app.healthcareapp.view.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import satya.app.healthcareapp.R
import satya.app.healthcareapp.databinding.FragmentSettingsBinding
import satya.app.healthcareapp.utils.Constants
import satya.app.healthcareapp.utils.Constants.Companion.PREF_NOTIFICATION_VALUE
import satya.app.healthcareapp.utils.PreferenceManager
import satya.app.healthcareapp.utils.Utils

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)

        initUI()

        return binding.root
    }

    private fun initUI() {
        val username =
            PreferenceManager.getSharedPreferences(requireContext(), Constants.PREF_USERNAME)
        binding.tvUsername.text = username

        val isNotificationEnabled = PreferenceManager.getSharedPreferencesBooleanValue(
            requireContext(), PREF_NOTIFICATION_VALUE
        )

        binding.switchNotifications.isChecked = isNotificationEnabled

        binding.ivEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_profileFragment)
        }

        var isDarkEnabled = false
        when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                isDarkEnabled = true
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                isDarkEnabled = false
            }
        }

        binding.switchChangeTheme.isChecked = isDarkEnabled

        binding.switchChangeTheme.setOnClickListener {
            Utils.setAppTheme(requireContext(), binding.switchChangeTheme.isChecked)
        }

        binding.llEditProfile.setOnClickListener {
            Utils.showProfileUpdateDialog(requireContext(), layoutInflater)
        }

        binding.llChangePassword.setOnClickListener {
            findNavController().navigate(R.id.action_nav_settings_to_changePasswordFragment)
        }

        binding.switchNotifications.setOnClickListener {
            if (binding.switchNotifications.isChecked) PreferenceManager.setSharedPreferences(
                requireContext(), PREF_NOTIFICATION_VALUE, true
            )
            else PreferenceManager.setSharedPreferences(
                requireContext(), PREF_NOTIFICATION_VALUE, false
            )
        }

        binding.llLanguage.setOnClickListener {
            Utils.toastMessage(requireContext(), "ivLanguage")
        }

        binding.llLogout.setOnClickListener {
            Utils.userLogout(requireContext())
        }
    }
}