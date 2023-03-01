package satya.app.healthcareapproomdb.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import satya.app.healthcareapproomdb.R
import satya.app.healthcareapproomdb.adapters.ViewDoctorAppointmentsAdapter
import satya.app.healthcareapproomdb.databinding.FragmentViewDoctorAppointmentsBinding
import satya.app.healthcareapproomdb.databinding.FragmentViewDoctorDetailsBinding
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.db.entities.BookAnAppointmentEntity
import satya.app.healthcareapproomdb.listeners.CommonItemListClickListener
import satya.app.healthcareapproomdb.models.DoctorListModel
import satya.app.healthcareapproomdb.utils.Constants
import satya.app.healthcareapproomdb.utils.PreferenceManager
import satya.app.healthcareapproomdb.utils.Utils
import satya.app.healthcareapproomdb.viewmodels.DoctorAppointmentViewModel

class ViewDoctorAppointmentsFragment : Fragment(),
    CommonItemListClickListener<BookAnAppointmentEntity> {

    private lateinit var binding: FragmentViewDoctorAppointmentsBinding
    private lateinit var adapter: ViewDoctorAppointmentsAdapter
    private lateinit var doctorsAppointmentList: List<BookAnAppointmentEntity>
    private lateinit var dialogBinding: FragmentViewDoctorDetailsBinding
    private lateinit var appDatabase: AppDatabase
    private val doctorAppointmentViewModel: DoctorAppointmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewDoctorAppointmentsBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    private fun initUI() {
        doctorAppointmentViewModel.getAllAppointments(
            requireContext(),
            PreferenceManager.getSharedPreferencesIntValues(
                requireContext(),
                Constants.PREF_USER_ID
            )
        )
            .observe(viewLifecycleOwner) { data ->
                doctorsAppointmentList = data
                Log.e("TAG", "initUI::doctorsAppointmentList : ${doctorsAppointmentList.size}")
                setRecords(doctorsAppointmentList)
            }
    }

    private fun setRecords(doctorsAppointmentList: List<BookAnAppointmentEntity>) {
        // adapter binding
        if (doctorsAppointmentList.isNotEmpty()) {
            adapter =
                ViewDoctorAppointmentsAdapter(
                    doctorsAppointmentList,
                    this@ViewDoctorAppointmentsFragment
                )
            binding.rcvViewDoctorsAppointments.adapter = adapter
            binding.rcvViewDoctorsAppointments.visibility = View.VISIBLE
            binding.tvNoAppointmentBooked.visibility = View.GONE
        } else {
            binding.rcvViewDoctorsAppointments.visibility = View.GONE
            binding.tvNoAppointmentBooked.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(item: BookAnAppointmentEntity) {
        val doctorsList =
            if (item.doctorCategory == "Physicians") Utils.getPhysiciansList() else Utils.getDoctorsList()
        val doctor: DoctorListModel = doctorsList.single {
            it.doctorId == item.doctorId
        }

        dialogBinding = FragmentViewDoctorDetailsBinding.inflate(layoutInflater)

        val dialog = Dialog(requireContext(), R.style.Theme_HealthCareApp_RoundedCornersDialog)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(true)

        dialogBinding.tvDoctorType.text = doctor.category
        dialogBinding.tvDoctorName.text = doctor.name
        dialogBinding.btnBookAnAppointment.visibility = View.GONE
        dialogBinding.ivCancel.visibility = View.VISIBLE
        dialogBinding.ivCancel.setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }

}