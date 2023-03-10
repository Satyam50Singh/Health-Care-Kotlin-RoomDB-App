package satya.app.healthcareapproomdb.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import satya.app.healthcareapproomdb.adapters.ViewAmbulanceBookingAdapter
import satya.app.healthcareapproomdb.databinding.FragmentViewAmbulanceBookingBinding
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.db.DataHandler
import satya.app.healthcareapproomdb.db.entities.BookAnAmbulanceEntity
import satya.app.healthcareapproomdb.listeners.CommonItemListClickListener
import satya.app.healthcareapproomdb.utils.Constants
import satya.app.healthcareapproomdb.utils.PreferenceManager
import satya.app.healthcareapproomdb.utils.Utils
import satya.app.healthcareapproomdb.viewmodels.AmbulanceBookingViewModel

class ViewAmbulanceBookingFragment : Fragment(),
    CommonItemListClickListener<BookAnAmbulanceEntity> {

    private lateinit var binding: FragmentViewAmbulanceBookingBinding
    private lateinit var adapter: ViewAmbulanceBookingAdapter
    private val viewModel: AmbulanceBookingViewModel by viewModels()

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
        var bookedAmbulanceList: List<BookAnAmbulanceEntity>

        viewModel.ambulanceBooking.observe(viewLifecycleOwner) { it ->
            when (it) {
                is DataHandler.ERROR -> {
                    Utils.toastMessage(requireContext(), it.errorMessage.toString())
                }
                is DataHandler.LOADING -> {
                    Log.e("TAG", "initUI: Loading $it")
                    Utils.toastMessage(requireContext(), "Loading ... please wait!")
                }
                is DataHandler.SUCCESS -> {
                    it.data?.let {
                        bookedAmbulanceList = it
                        if (bookedAmbulanceList.isNotEmpty()) {
                            adapter = ViewAmbulanceBookingAdapter(
                                bookedAmbulanceList,
                                this
                            )
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
        }
    }

    override fun onItemClick(item: BookAnAmbulanceEntity) {
        Log.e("TAG", "onItemClick : item.ambulanceId : ${item.ambulanceId}")
    }
}