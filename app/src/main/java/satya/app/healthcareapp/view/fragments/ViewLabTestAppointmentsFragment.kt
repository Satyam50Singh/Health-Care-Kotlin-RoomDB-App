package satya.app.healthcareapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import satya.app.healthcareapp.adapters.ViewLabTestAppointmentsAdapter
import satya.app.healthcareapp.databinding.FragmentViewLabTestAppointmentsBinding
import satya.app.healthcareapp.db.Database
import satya.app.healthcareapp.listeners.CommonItemListClickListener
import satya.app.healthcareapp.models.BookLabTestModel
import satya.app.healthcareapp.utils.Constants

class ViewLabTestAppointmentsFragment : Fragment(), CommonItemListClickListener<BookLabTestModel> {

    private lateinit var binding: FragmentViewLabTestAppointmentsBinding
    private lateinit var adapter: ViewLabTestAppointmentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewLabTestAppointmentsBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    private fun initUI() {
        val db = Database(requireContext(), Constants.DB_NAME, null, 1)
        val bookedLabTestList = db.getBookedLabTestList()
        Log.e("TAG", "initUI::bookedLabTestList : ${bookedLabTestList.size}")

        if (bookedLabTestList.size > 0) {
            adapter = ViewLabTestAppointmentsAdapter(bookedLabTestList, this)
            binding.rcvViewLabTestBooking.adapter = adapter
            binding.tvNoLabTestBooked.visibility = View.GONE
            binding.rcvViewLabTestBooking.visibility = View.VISIBLE
        } else {
            binding.tvNoLabTestBooked.visibility = View.VISIBLE
            binding.rcvViewLabTestBooking.visibility = View.GONE
        }
    }

    override fun onItemClick(item: BookLabTestModel) {
        Log.e("TAG", "onItemClick: ${item.labTitle}")
    }
}