package satya.app.healthcareapproomdb.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import satya.app.healthcareapproomdb.R
import satya.app.healthcareapproomdb.adapters.ViewDoctorAppointmentsAdapter
import satya.app.healthcareapproomdb.databinding.FragmentViewDoctorAppointmentsBinding
import satya.app.healthcareapproomdb.databinding.FragmentViewDoctorDetailsBinding
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.db.entities.BookAnAppointmentEntity
import satya.app.healthcareapproomdb.listeners.CommonItemListClickListener
import satya.app.healthcareapproomdb.models.DoctorListModel
import satya.app.healthcareapproomdb.utils.Utils

class ViewDoctorAppointmentsFragment : Fragment(),
    CommonItemListClickListener<BookAnAppointmentEntity> {

    private lateinit var binding: FragmentViewDoctorAppointmentsBinding
    private lateinit var adapter: ViewDoctorAppointmentsAdapter
    private lateinit var doctorsAppointmentList: List<BookAnAppointmentEntity>
    private lateinit var dialogBinding: FragmentViewDoctorDetailsBinding
    private lateinit var appDatabase: AppDatabase

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
        appDatabase = AppDatabase.getDatabase(requireContext())

        GlobalScope.launch {
            doctorsAppointmentList = appDatabase.doctorAppointmentDao().getAllAppointmentsRecord()
            Log.e("TAG", "initUI::doctorsAppointmentList : ${doctorsAppointmentList.size}")
            setRecords(doctorsAppointmentList)
        }
    }

    private suspend fun setRecords(doctorsAppointmentList: List<BookAnAppointmentEntity>) {
        // adapter binding
        withContext(Dispatchers.Main) {
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