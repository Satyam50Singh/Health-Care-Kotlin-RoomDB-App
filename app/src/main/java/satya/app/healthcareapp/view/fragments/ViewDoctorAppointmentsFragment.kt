package satya.app.healthcareapp.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import satya.app.healthcareapp.R
import satya.app.healthcareapp.adapters.ViewDoctorAppointmentsAdapter
import satya.app.healthcareapp.databinding.FragmentViewDoctorAppointmentsBinding
import satya.app.healthcareapp.databinding.FragmentViewDoctorDetailsBinding
import satya.app.healthcareapp.db.Database
import satya.app.healthcareapp.listeners.CommonItemListClickListener
import satya.app.healthcareapp.models.BookAnAppointmentModel
import satya.app.healthcareapp.models.DoctorListModel
import satya.app.healthcareapp.utils.Utils

class ViewDoctorAppointmentsFragment : Fragment(),
    CommonItemListClickListener<BookAnAppointmentModel> {

    private lateinit var binding: FragmentViewDoctorAppointmentsBinding
    private lateinit var adapter: ViewDoctorAppointmentsAdapter
    private lateinit var db: Database
    private lateinit var doctorsAppointmentList: ArrayList<BookAnAppointmentModel>
    private lateinit var dialogBinding: FragmentViewDoctorDetailsBinding

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
        db = Database(requireContext(), "healthcare", null, 1)
        doctorsAppointmentList = db.getDoctorsAppointmentList()
        Log.e("TAG", "initUI::doctorsAppointmentList : ${doctorsAppointmentList.size}")

        // adapter binding
        if(doctorsAppointmentList.size > 0) {
            adapter =
                ViewDoctorAppointmentsAdapter(requireContext(), doctorsAppointmentList, this)
            binding.rcvViewDoctorsAppointments.adapter = adapter
            binding.rcvViewDoctorsAppointments.visibility = View.VISIBLE
            binding.tvNoAppointmentBooked.visibility = View.GONE
        }else {
            binding.rcvViewDoctorsAppointments.visibility = View.GONE
            binding.tvNoAppointmentBooked.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(item: BookAnAppointmentModel) {
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