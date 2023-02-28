package satya.app.healthcareapproomdb.view.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import satya.app.healthcareapproomdb.R
import satya.app.healthcareapproomdb.adapters.DatesDayAdapter
import satya.app.healthcareapproomdb.databinding.DialogBookAnAppointmentBinding
import satya.app.healthcareapproomdb.databinding.FragmentViewDoctorDetailsBinding
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.db.entities.BookAnAppointmentEntity
import satya.app.healthcareapproomdb.listeners.CommonClickListener
import satya.app.healthcareapproomdb.models.DoctorListModel
import satya.app.healthcareapproomdb.utils.Constants
import satya.app.healthcareapproomdb.utils.DateTimeUtils
import satya.app.healthcareapproomdb.utils.PreferenceManager
import satya.app.healthcareapproomdb.utils.Utils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ViewDoctorDetailsFragment : Fragment(), CommonClickListener {

    private lateinit var binding: FragmentViewDoctorDetailsBinding
    private lateinit var dialogBinding: DialogBookAnAppointmentBinding
    private lateinit var next10Dates: MutableMap<String, String>
    private var selectedDate: String = ""
    private var selectedDay: String = ""
    private var selectedTimeSlot: String = ""
    private lateinit var doctor: DoctorListModel
    private val args by navArgs<ViewDoctorDetailsFragmentArgs>()
    private lateinit var appDatabase: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewDoctorDetailsBinding.inflate(layoutInflater, container, false)
        initUI()
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initUI() {
        doctor = args.selectedDoctor

        binding.tvDoctorName.text = doctor.name
        binding.tvDoctorType.text = doctor.category
        binding.tvDoctorAbout.text = doctor.doctorHistory
        if (doctor.name.lowercase().contains("archana") || doctor.name.lowercase()
                .contains("shivani")
        ) {
            binding.ivDoctorImage.setImageDrawable(resources.getDrawable(R.drawable.female_doctor))
        } else {
            binding.ivDoctorImage.setImageDrawable(resources.getDrawable(R.drawable.male_doctor))
        }
        binding.btnBookAnAppointment.setOnClickListener {
            showBookAppointmentDialog()
        }

        appDatabase = AppDatabase.getDatabase(requireContext())
    }

    @SuppressLint("SimpleDateFormat")
    private fun showBookAppointmentDialog() {
        dialogBinding = DialogBookAnAppointmentBinding.inflate(layoutInflater)

        val dialog = Dialog(requireContext(), R.style.Theme_HealthCareApp_RoundedCornersDialog)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(true)
        val dateFormat: DateFormat = SimpleDateFormat(Constants.monthYearFormat)
        val date = Date()
        Log.e("TAG", "showBookAppointmentDialog: $date")

        dialogBinding.tvCurrentMonthYear.text = dateFormat.format(date)
        dialogBinding.tvDoctorName.text = doctor.name
        dialogBinding.tvDoctorType.text = doctor.category

        val calendar: Calendar = Calendar.getInstance()
        next10Dates = DateTimeUtils.loopNextDates(calendar)

        Log.e("TAG", "loopNextDates: $next10Dates")

        dialogBinding.rcvDatesDays.adapter = DatesDayAdapter(requireContext(), next10Dates, this)

        next10Dates.keys.map {
            Log.e("TAG", "loopNextDates: $it and ${next10Dates[it]}")
        }

        dialogBinding.btnBookAnAppointment.setOnClickListener {
            selectedTimeSlot = when {
                dialogBinding.rbSlot1.isChecked -> "09:00 AM"
                dialogBinding.rbSlot2.isChecked -> "11:00 AM"
                dialogBinding.rbSlot3.isChecked -> "02:00 PM"
                dialogBinding.rbSlot4.isChecked -> "05:00 PM"
                else -> "No Slot Selected"
            }

            if (selectedDate.isEmpty() || selectedDay.isEmpty()) {
                Utils.toastMessage(requireContext(), getString(R.string.please_select_date))
            } else if (selectedTimeSlot.isEmpty()) {
                Utils.toastMessage(requireContext(), getString(R.string.please_select_time_slot))
            } else {
                // Room database Implementation
                val bookAnAppointmentEntity = BookAnAppointmentEntity(
                    null,
                    PreferenceManager.getSharedPreferencesIntValues(
                        requireContext(), Constants.PREF_USER_ID
                    ),
                    PreferenceManager.getSharedPreferences(
                        requireContext(), Constants.PREF_USERNAME
                    ),
                    selectedDate,
                    selectedDay,
                    selectedTimeSlot,
                    doctor.doctorId,
                    doctor.name,
                    doctor.category
                )

                GlobalScope.launch(Dispatchers.IO) {
                    appDatabase.doctorAppointmentDao().bookAppointment(bookAnAppointmentEntity)
                }

                Utils.toastMessage(
                    requireContext(),
                    getString(R.string.appointment_booked_successfully)
                )
                dialog.dismiss()
                findNavController().navigateUp()
            }
        }

        dialog.show()
    }

    // when user will select date and day for appointment.
    override fun onClickItem(position: Int) {
        val nextDates = ArrayList<String>()
        val nextDays = ArrayList<String>()
        next10Dates.keys.map {
            nextDates.add(it)
            next10Dates[it]?.let { it1 -> nextDays.add(it1) }
        }

        selectedDate = nextDates[position]
        selectedDay = nextDays[position]
    }

}