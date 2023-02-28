package satya.app.healthcareapproomdb.view.fragments

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
import satya.app.healthcareapproomdb.adapters.ViewAmbulanceBookingAdapter
import satya.app.healthcareapproomdb.databinding.FragmentViewAmbulanceBookingBinding
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.db.entities.BookAnAmbulanceEntity
import satya.app.healthcareapproomdb.listeners.CommonItemListClickListener

class ViewAmbulanceBookingFragment : Fragment(), CommonItemListClickListener<BookAnAmbulanceEntity> {

    private lateinit var binding: FragmentViewAmbulanceBookingBinding
    private lateinit var adapter: ViewAmbulanceBookingAdapter
    private lateinit var appDatabase: AppDatabase

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
        appDatabase = AppDatabase.getDatabase(requireContext())

        GlobalScope.launch {
            val bookedAmbulanceList =  appDatabase.ambulanceBookingDao().getAllAmbulanceBookingRecord()
            withContext(Dispatchers.Main) {
                Log.e("TAG", "initUI::bookedAmbulanceList : ${bookedAmbulanceList.size}")

                if (bookedAmbulanceList.isNotEmpty()) {
                    adapter = ViewAmbulanceBookingAdapter(bookedAmbulanceList, this@ViewAmbulanceBookingFragment)
                    binding.rcvViewAmbulanceBooking.adapter = adapter
                    binding.tvNoAmbulanceBooked.visibility = View.GONE
                    binding.rcvViewAmbulanceBooking.visibility = View.VISIBLE
                } else {
                    binding.tvNoAmbulanceBooked.visibility = View.VISIBLE
                    binding.rcvViewAmbulanceBooking.visibility = View.GONE
                }
            }
        }
    }

    override fun onItemClick(item: BookAnAmbulanceEntity) {
        Log.e("TAG", "onItemClick : item.ambulanceId : ${item.ambulanceId}")
    }
}