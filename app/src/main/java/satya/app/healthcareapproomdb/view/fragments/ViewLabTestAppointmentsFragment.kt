package satya.app.healthcareapproomdb.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import satya.app.healthcareapproomdb.adapters.ViewLabTestAppointmentsAdapter
import satya.app.healthcareapproomdb.databinding.FragmentViewLabTestAppointmentsBinding
import satya.app.healthcareapproomdb.db.entities.BookLabTestEntity
import satya.app.healthcareapproomdb.listeners.CommonItemListClickListener
import satya.app.healthcareapproomdb.utils.Constants
import satya.app.healthcareapproomdb.utils.PreferenceManager
import satya.app.healthcareapproomdb.viewmodels.BookLabTestViewModel

class ViewLabTestAppointmentsFragment : Fragment(), CommonItemListClickListener<BookLabTestEntity> {

    private lateinit var binding: FragmentViewLabTestAppointmentsBinding
    private lateinit var adapter: ViewLabTestAppointmentsAdapter
    private val viewModel: BookLabTestViewModel by viewModels()


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
        viewModel.getAllLabBooking(
            PreferenceManager.getSharedPreferencesIntValues(
                requireContext(),
                Constants.PREF_USER_ID
            )
        )
            .observe(viewLifecycleOwner) { data ->
                run {
                    if (data.isNotEmpty()) {
                        adapter = ViewLabTestAppointmentsAdapter(
                            data,
                            this@ViewLabTestAppointmentsFragment
                        )
                        binding.rcvViewLabTestBooking.adapter = adapter
                        binding.tvNoLabTestBooked.visibility = View.GONE
                        binding.rcvViewLabTestBooking.visibility = View.VISIBLE

                    } else {
                        binding.tvNoLabTestBooked.visibility = View.VISIBLE
                        binding.rcvViewLabTestBooking.visibility = View.GONE
                    }
                }
            }
    }

    override fun onItemClick(item: BookLabTestEntity) {
        Log.e("TAG", "onItemClick: ${item.labTitle}")
    }
}