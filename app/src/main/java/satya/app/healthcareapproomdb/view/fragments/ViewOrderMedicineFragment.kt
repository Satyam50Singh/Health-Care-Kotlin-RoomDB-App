package satya.app.healthcareapproomdb.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import satya.app.healthcareapproomdb.adapters.ViewMedicineOrderAdapter
import satya.app.healthcareapproomdb.databinding.FragmentViewOrderMedicineBinding
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.utils.Constants
import satya.app.healthcareapproomdb.utils.PreferenceManager

class ViewOrderMedicineFragment : Fragment() {

    private lateinit var binding: FragmentViewOrderMedicineBinding
    private lateinit var adapter: ViewMedicineOrderAdapter
    private lateinit var appDatabase: AppDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewOrderMedicineBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    private fun initUI() {
        appDatabase = AppDatabase.getDatabase(requireContext())

        // fetch orders list
        GlobalScope.launch {
            val ordersList = appDatabase.orderMedicineDao().getAllMedicineOrders(
                PreferenceManager.getSharedPreferencesIntValues(
                    requireContext(), Constants.PREF_USER_ID
                )
            )
            Log.e("TAG", "initUI::bookedLabTestList : ${ordersList.size}")

            if (ordersList.isNotEmpty()) {
                adapter = ViewMedicineOrderAdapter(requireContext(), ordersList)
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