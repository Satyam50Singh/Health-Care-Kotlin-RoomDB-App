package satya.app.healthcareapp.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import satya.app.healthcareapp.R
import satya.app.healthcareapp.adapters.OrderMedicineAdapter
import satya.app.healthcareapp.adapters.OrderMedicineAdapter.Companion.selectedMedicineList
import satya.app.healthcareapp.databinding.FragmentOrderMedicineBinding
import satya.app.healthcareapp.models.MedicineModel
import satya.app.healthcareapp.utils.Constants
import satya.app.healthcareapp.utils.PreferenceManager
import satya.app.healthcareapp.utils.Utils

class OrderMedicineFragment : Fragment() {
    private lateinit var adapter: OrderMedicineAdapter
    private var medicineList = ArrayList<MedicineModel>()

    companion object {

        private lateinit var binding: FragmentOrderMedicineBinding

        var cartCount = 0
        fun setCartCount() {
            if (cartCount > 0) binding.btnCheckCart.text = "Cart ($cartCount)"
            else if (cartCount == 0) binding.btnCheckCart.text = "Cart"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderMedicineBinding.inflate(layoutInflater, container, false)

        initUI()

        setCartCount()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initUI() {
        medicineList = Utils.getMedicineList()
        adapter = OrderMedicineAdapter(requireContext(), medicineList)
        binding.rcvMedicines.adapter = adapter

        binding.srlMedicineRcv.setOnRefreshListener {
            Log.e("TAG", "initUI: setOnRefreshListener")

            medicineList = Utils.getMedicineList()
            adapter.notifyDataSetChanged()

            binding.srlMedicineRcv.isRefreshing = false
        }


        binding.btnCheckCart.setOnClickListener {
            Log.e("TAG", "initUI: ${selectedMedicineList.size}")
            for (element in selectedMedicineList) {
                Log.e(
                    "TAG", "onBindViewHolder: ${element.medicineName} --  ${element.quantity}"
                )
            }

            findNavController().navigate(R.id.action_nav_orderMedicineFragment_to_medicineCartFragment)
        }

        // search functionality
        binding.svSearchMedicine.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterMedicineList(newText)
                return true
            }

        })
    }

    // this method is used to show the quantity of selected Medicines
    override fun onResume() {
        super.onResume()

        if (selectedMedicineList.size == 0) {
            val selectedMedicineKeys = PreferenceManager.getSharedPreferences(
                requireContext(), Constants.PREF_CART_MEDICINE_IDS_KEYS
            )
            val selectedMedicineQuantities = PreferenceManager.getSharedPreferences(
                requireContext(), Constants.PREF_CART_MEDICINE_QUANTITY_VALUES
            )

            Log.e("TAG", " onResume :selectedMedicineKeys $selectedMedicineKeys")
            Log.e("TAG", " onResume :selectedMedicineQuantities $selectedMedicineQuantities")
            Log.e("TAG", "onResume:selectedMedicineList.size ${selectedMedicineList.size}")

            if (selectedMedicineKeys != null && selectedMedicineQuantities != null) {
                if (selectedMedicineKeys.isNotEmpty() && selectedMedicineQuantities.isNotEmpty()) {
                    val selectedListIds: List<String>? =
                        selectedMedicineKeys
                            ?.slice(1..(selectedMedicineKeys.length - 2))
                            ?.split(", ")
                            ?.toList()
                    val selectedListQuantities: List<String>? =
                        selectedMedicineQuantities
                            ?.slice(1..(selectedMedicineQuantities.length - 2))
                            ?.split(", ")
                            ?.toList()

                    cartCount = selectedListIds?.size!!
                    setCartCount()

                    if (selectedListIds != null) {
                        for (idIndex in selectedListIds.indices) {
                            val id = selectedListIds[idIndex].toInt()
                            val quantity = selectedListQuantities?.get(idIndex)?.toInt()
                            Log.e("TAG", "id: $id and quantity : $quantity")
                            for (medicine in medicineList) {
                                if (medicine.medicineId == id) {
                                    if (quantity != null) {
                                        medicine.quantity = quantity
                                    }
                                    selectedMedicineList.add(medicine)
                                }
                            }
                        }
                    }
                }
            }
        }

        try {
            for (medicine in medicineList) {
                for (selectedMedicine in selectedMedicineList) {
                    if (medicine.medicineId == selectedMedicine.medicineId) {
                        medicine.quantity = selectedMedicine.quantity
                        break
                    }
                }
            }

            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun filterMedicineList(newText: String?) {
        val filteredList = ArrayList<MedicineModel>()
        for (medicine in medicineList) {
            if(medicine.medicineName.lowercase().contains(newText?.lowercase().toString())) {
                filteredList.add(medicine)
            }
        }

        if(filteredList.size > 0) {
            adapter.setFilteredList(filteredList)
        }
    }
}
