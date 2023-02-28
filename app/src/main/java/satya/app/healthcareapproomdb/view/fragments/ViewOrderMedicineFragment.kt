package satya.app.healthcareapproomdb.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import satya.app.healthcareapproomdb.adapters.ViewMedicineOrderAdapter
import satya.app.healthcareapproomdb.databinding.FragmentViewOrderMedicineBinding
import satya.app.healthcareapproomdb.db.Database
import satya.app.healthcareapproomdb.utils.Constants

class ViewOrderMedicineFragment : Fragment() {

    private lateinit var binding: FragmentViewOrderMedicineBinding
    private lateinit var adapter: ViewMedicineOrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewOrderMedicineBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    private fun initUI() {
        // fetch orders list
        val db = Database(requireContext(), Constants.DB_NAME, null, 1)
        val ordersList = db.getMedicineOrdersList()
        Log.e("TAG", "initUI::bookedLabTestList : ${ordersList.size}")

        if (ordersList.size > 0) {
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