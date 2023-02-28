package satya.app.healthcareapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import satya.app.healthcareapp.R
import satya.app.healthcareapp.databinding.FragmentProfileBinding
import satya.app.healthcareapp.listeners.RefreshFragmentListener
import satya.app.healthcareapp.utils.Constants.Companion.PREF_AADHAAR_NUMBER
import satya.app.healthcareapp.utils.Constants.Companion.PREF_EMAIL
import satya.app.healthcareapp.utils.Constants.Companion.PREF_EMERGENCY_CONTACT
import satya.app.healthcareapp.utils.Constants.Companion.PREF_PERSONAL_CONTACT
import satya.app.healthcareapp.utils.Constants.Companion.PREF_USERNAME
import satya.app.healthcareapp.utils.Constants.Companion.PREF_USER_ADDRESS
import satya.app.healthcareapp.utils.PreferenceManager
import satya.app.healthcareapp.utils.Utils.Companion.showProfileUpdateDialog

class ProfileFragment : Fragment(), RefreshFragmentListener {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        setUserDetails()

        initUI()
        return binding.root
    }

    private fun initUI() {
        binding.fabEditProfile.setOnClickListener {
            showProfileUpdateDialog(requireContext(), layoutInflater, this)
        }
    }

    private fun setUserDetails() {
        binding.tvUserName.text =
            PreferenceManager.getSharedPreferences(requireContext(), PREF_USERNAME)?.uppercase()
        binding.tvUserEmail.text =
            PreferenceManager.getSharedPreferences(requireContext(), PREF_EMAIL)
        try {
            val personalContact =
                PreferenceManager.getSharedPreferences(requireContext(), PREF_PERSONAL_CONTACT)

            if (personalContact!!.isNotEmpty()) {
                binding.tvPersonalContact.text = personalContact
            } else {
                binding.tvPersonalContact.text = getString(R.string.add_your_personal_contact)
            }
            val address =
                PreferenceManager.getSharedPreferences(requireContext(), PREF_USER_ADDRESS)

            if (address!!.isNotEmpty()) {
                binding.tvAddress.text = address
            } else {
                binding.tvAddress.text = getString(R.string.add_your_location_address)
            }
            val emergencyContact =
                PreferenceManager.getSharedPreferences(requireContext(), PREF_EMERGENCY_CONTACT)

            if (emergencyContact!!.isNotEmpty()) {
                binding.tvEmergencyContact.text = emergencyContact
            } else {
                binding.tvEmergencyContact.text = getString(R.string.add_your_emergency_contact)
            }
            val aadhaarNumber =
                PreferenceManager.getSharedPreferences(requireContext(), PREF_AADHAAR_NUMBER)

            if (aadhaarNumber!!.isNotEmpty()) {
                binding.tvAadhaarNumber.text = aadhaarNumber
            } else {
                binding.tvAadhaarNumber.text = getString(R.string.add_your_aadhaar_number)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // refreshing the profile fragment
    override fun refresh() {
        setUserDetails()
    }
}