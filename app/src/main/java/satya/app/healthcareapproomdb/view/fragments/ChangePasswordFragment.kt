package satya.app.healthcareapproomdb.view.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import satya.app.healthcareapproomdb.R
import satya.app.healthcareapproomdb.databinding.FragmentChangePasswordBinding
import satya.app.healthcareapproomdb.db.Database
import satya.app.healthcareapproomdb.utils.Constants
import satya.app.healthcareapproomdb.utils.PreferenceManager
import satya.app.healthcareapproomdb.utils.Utils
import satya.app.healthcareapproomdb.view.activites.DashboardActivity
import satya.app.healthcareapproomdb.view.activites.LoginActivity

class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    private fun initUI() {

        binding.btnUpdatePassword.setOnClickListener {
            val enteredPassword = binding.etOldPassword.text.toString().trim()
            val newPassword = binding.etNewPassword.text.toString().trim()
            val confirmNewPassword = binding.etConfirmNewPassword.text.toString().trim()

            if (enteredPassword.isEmpty()) {
                Utils.toastMessage(requireContext(), getString(R.string.please_enter_old_password))
            } else if (newPassword.isEmpty()) {
                Utils.toastMessage(requireContext(), getString(R.string.please_enter_new_password))
            } else if (confirmNewPassword.isEmpty()) {
                Utils.toastMessage(
                    requireContext(),
                    getString(R.string.please_renter_your_password)
                )
            } else if (newPassword != confirmNewPassword) {
                Utils.toastMessage(requireContext(), getString(R.string.password_do_not_match))
            } else {
                val password =
                    PreferenceManager.getSharedPreferences(
                        requireContext(),
                        Constants.PREF_PASSWORD
                    )
                if (password == enteredPassword) {
                    // change password
                    val db = Database(requireContext(), Constants.DB_NAME, null, 1)
                    val changePasswordResult = db.changePassword(enteredPassword, newPassword)

                    if (changePasswordResult) {
                        // password updated
                        PreferenceManager.setSharedPreferences(
                            requireContext(),
                            Constants.PREF_PASSWORD,
                            newPassword
                        )
                        PreferenceManager.setSharedPreferences(
                            requireContext(),
                            Constants.PREF_REMEMBER_ME,
                            false
                        )
                        PreferenceManager.clearAllSharedPreferences(requireContext())
                        Utils.switchActivity(requireContext(), LoginActivity::class.java)
                        (context as Activity).overridePendingTransition(
                            R.anim.enter_from_left,
                            R.anim.exit_in_right
                        )
                        (activity as DashboardActivity).finishAffinity()
                        Utils.toastMessage(requireContext(), getString(R.string.password_changed_successfully))

                    }
                } else {
                    Utils.toastMessage(
                        requireContext(),
                        getString(R.string.old_password_do_not_match)
                    )
                }
            }
        }
    }
}