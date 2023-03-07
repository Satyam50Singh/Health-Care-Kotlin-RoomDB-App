package satya.app.healthcareapproomdb.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import satya.app.healthcareapproomdb.adapters.ViewMedicineOrderAdapter
import satya.app.healthcareapproomdb.databinding.FragmentViewOrderMedicineBinding
import satya.app.healthcareapproomdb.utils.Constants
import satya.app.healthcareapproomdb.utils.PreferenceManager
import satya.app.healthcareapproomdb.viewmodels.OrderMedicineViewModel

class ViewOrderMedicineFragment : Fragment() {

    private lateinit var binding: FragmentViewOrderMedicineBinding
    private lateinit var adapter: ViewMedicineOrderAdapter

    private val viewModel: OrderMedicineViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewOrderMedicineBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    private fun initUI() {
        viewModel.getAllOrders(
            PreferenceManager.getSharedPreferencesIntValues(
                requireContext(), Constants.PREF_USER_ID
            )
        )
            .observe(viewLifecycleOwner) { data ->
                if (data.isNotEmpty()) {
                    Log.e("TAG", "initUI::ViewOrderMedicineFragment : ${data.size}")
                    if (data.isNotEmpty()) {
                        adapter = ViewMedicineOrderAdapter(requireContext(), data)
                        binding.rcvViewMedicineOrder.adapter = adapter
                        binding.rcvViewMedicineOrder.visibility = View.VISIBLE
                        binding.tvNoOrderFound.visibility = View.GONE
                    } else {
                        binding.rcvViewMedicineOrder.visibility = View.GONE
                        binding.tvNoOrderFound.visibility = View.VISIBLE
                    }
                }

            }
    }
}