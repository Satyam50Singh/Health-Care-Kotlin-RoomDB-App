package satya.app.healthcareapp.view.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import satya.app.healthcareapp.R
import satya.app.healthcareapp.databinding.FragmentBookAmbulanceBinding
import satya.app.healthcareapp.db.Database
import satya.app.healthcareapp.models.AmbulanceModel
import satya.app.healthcareapp.models.BookAnAmbulanceModel
import satya.app.healthcareapp.utils.Constants
import satya.app.healthcareapp.utils.PreferenceManager
import satya.app.healthcareapp.utils.Utils
import java.util.*

class BookAnAmbulanceFragment : Fragment() {

    private lateinit var binding: FragmentBookAmbulanceBinding
    private lateinit var ambulanceDetails: AmbulanceModel
    private val args by navArgs<BookAnAmbulanceFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookAmbulanceBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    private fun initUI() {
        ambulanceDetails = args.selectedAmbulance

        with(binding) {
            with(ambulanceDetails) {
                tvAmbulanceTitle.text = name
                tvAmbulanceCategory.text = type
                tvAmbulanceAddress.text = address

                tvPickUpDate.setOnClickListener {
                    // current date variables
                    val calendar: Calendar = Calendar.getInstance()
                    val mYear: Int = calendar.get(Calendar.YEAR)
                    val mMonth: Int = calendar.get(Calendar.MONTH)
                    val mDay: Int = calendar.get(Calendar.DAY_OF_MONTH)
                    var selectedDate: String = ""

                    val datePickerDialog = DatePickerDialog(
                        requireContext(),
                        { _: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                            run {
                                selectedDate += year.toString()

                                selectedDate += if (month < 10) {
                                    "-0${month + 1}-"
                                } else {
                                    { month + 1 }.toString()
                                }

                                selectedDate += if (dayOfMonth < 10) {
                                    "0$dayOfMonth"
                                } else {
                                    dayOfMonth.toString()
                                }
                                tvPickUpDate.setText(selectedDate.toString())
                            }
                        },
                        mYear,
                        mMonth,
                        mDay
                    )

                    val now = System.currentTimeMillis() - 1000
                    datePickerDialog.datePicker.minDate = now
                    datePickerDialog.datePicker.maxDate = now + (1000 * 60 * 60 * 24 * 10)

                    datePickerDialog.show()
                }

                tvPickUpTime.setOnClickListener {
                    if (tvPickUpDate.text.isNotEmpty()) {
                        val calendar: Calendar = Calendar.getInstance()
                        val hour = calendar.get(Calendar.HOUR)
                        val minute = calendar.get(Calendar.MINUTE)
                        val timePickerDialog = TimePickerDialog(
                            requireContext(), { _, p1: Int, p2: Int ->
                                run {
                                    tvPickUpTime.text = "$p1:$p2"
                                }
                            }, hour, minute, true
                        ).show()
                    } else {
                        Utils.toastMessage(
                            requireContext(),
                            getString(R.string.please_select_pick_up_date)
                        )
                    }
                }

                btnBookAnAmbulance.setOnClickListener {
                    if (isValidate()) {
                        val db = Database(requireContext(), Constants.DB_NAME, null, 1)
                        val bookAnAmbulanceModel = BookAnAmbulanceModel(
                            -1,
                            PreferenceManager.getSharedPreferencesIntValues(
                                requireContext(), Constants.PREF_USER_ID
                            ),
                            PreferenceManager.getSharedPreferences(
                                requireContext(), Constants.PREF_USERNAME
                            ).toString(),
                            ambulanceId,
                            name,
                            type,
                            address,
                            etPickUpLocation.text.toString(),
                            tvPickUpDate.text.toString(),
                            tvPickUpTime.text.toString(),
                            etDropLocation.text.toString(),
                            etPhone.text.toString()
                        )
                        val result = db.bookAnAmbulance(bookAnAmbulanceModel)
                        if (result) {
                            clearControls()
                            Snackbar.make(
                                it,
                                getString(R.string.ambulance_booked_successfully),
                                Snackbar.LENGTH_LONG
                            ).show()
                            findNavController().navigateUp()
                        } else {
                            Utils.toastMessage(
                                requireContext(), getString(R.string.something_wrong_happened)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun clearControls() {
        with(binding) {
            etPickUpLocation.setText("")
            tvPickUpDate.text = ""
            tvPickUpTime.text = ""
            etDropLocation.setText("")
            etPhone.setText("")
        }

    }

    private fun isValidate(): Boolean {
        with(binding) {
            if (etPickUpLocation.text.isEmpty()) {
                Utils.toastMessage(
                    requireContext(), getString(R.string.please_enter_pick_up_location)
                )
                return false
            } else if (tvPickUpDate.text.isEmpty()) {
                Utils.toastMessage(
                    requireContext(), getString(R.string.please_select_pick_up_date)
                )
                return false
            } else if (tvPickUpTime.text.isEmpty()) {
                Utils.toastMessage(
                    requireContext(), getString(R.string.please_enter_pick_up_time)
                )
                return false
            } else if (etDropLocation.text.isEmpty()) {
                Utils.toastMessage(
                    requireContext(), getString(R.string.please_enter_drop_location)
                )
                return false
            } else if (etPhone.text.isEmpty()) {
                Utils.toastMessage(
                    requireContext(), getString(R.string.please_enter_mobile_number)
                )
                return false
            }
        }
        return true
    }

}