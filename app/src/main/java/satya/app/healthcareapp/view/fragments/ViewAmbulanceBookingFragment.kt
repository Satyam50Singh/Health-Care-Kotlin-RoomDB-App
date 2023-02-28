package satya.app.healthcareapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import satya.app.healthcareapp.adapters.ViewAmbulanceBookingAdapter
import satya.app.healthcareapp.databinding.FragmentViewAmbulanceBookingBinding
import satya.app.healthcareapp.db.Database
import satya.app.healthcareapp.listeners.CommonItemListClickListener
import satya.app.healthcareapp.models.BookAnAmbulanceModel

class ViewAmbulanceBookingFragment : Fragment(), CommonItemListClickListener<BookAnAmbulanceModel> {

    private lateinit var binding: FragmentViewAmbulanceBookingBinding
    private lateinit var adapter: ViewAmbulanceBookingAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewAmbulanceBookingBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    private fun initUI() {
        val db = Database(requireContext(), "healthcare", null, 1)
        val bookedAmbulanceList = db.getBookedAmbulanceList()
        Log.e("TAG", "initUI::bookedAmbulanceList : ${bookedAmbulanceList.size}")

        if (bookedAmbulanceList.size > 0) {
            adapter = ViewAmbulanceBookingAdapter(bookedAmbulanceList, this)
            binding.rcvViewAmbulanceBooking.adapter = adapter
            binding.tvNoAmbulanceBooked.visibility = View.GONE
            binding.rcvViewAmbulanceBooking.visibility = View.VISIBLE
        } else {
            binding.tvNoAmbulanceBooked.visibility = View.VISIBLE
            binding.rcvViewAmbulanceBooking.visibility = View.GONE
        }
    }

    override fun onItemClick(item: BookAnAmbulanceModel) {
        Log.e("TAG", "onItemClick : item.ambulanceId : ${item.ambulanceId}")
    }
}