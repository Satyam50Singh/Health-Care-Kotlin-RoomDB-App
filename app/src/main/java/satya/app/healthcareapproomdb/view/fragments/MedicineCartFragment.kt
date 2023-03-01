package satya.app.healthcareapproomdb.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import satya.app.healthcareapproomdb.R
import satya.app.healthcareapproomdb.adapters.MedicineCartAdapter
import satya.app.healthcareapproomdb.adapters.OrderMedicineAdapter.Companion.selectedMedicineList
import satya.app.healthcareapproomdb.databinding.DialogOrderMedicineBinding
import satya.app.healthcareapproomdb.databinding.FragmentMedicineCartBinding
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.db.Database
import satya.app.healthcareapproomdb.db.entities.OrderMedicineEntity
import satya.app.healthcareapproomdb.models.MedicineModel
import satya.app.healthcareapproomdb.utils.Constants
import satya.app.healthcareapproomdb.utils.PreferenceManager
import satya.app.healthcareapproomdb.utils.Utils
import satya.app.healthcareapproomdb.view.fragments.OrderMedicineFragment.Companion.cartCount
import satya.app.healthcareapproomdb.view.fragments.OrderMedicineFragment.Companion.setCartCount

class MedicineCartFragment : Fragment() {

    private lateinit var binding: FragmentMedicineCartBinding
    private lateinit var cartList: ArrayList<MedicineModel>
    private lateinit var adapter: MedicineCartAdapter
    private lateinit var dialogBinding: DialogOrderMedicineBinding
    private lateinit var appDatabase: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMedicineCartBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    private fun initUI() {
        appDatabase = AppDatabase.getDatabase(requireContext())
        cartList = selectedMedicineList

        if (cartList.size > 0) {
            adapter = MedicineCartAdapter(requireContext(), cartList)
            binding.rcvCartItems.adapter = adapter
            binding.rcvCartItems.visibility = View.VISIBLE
            binding.tvCartEmpty.visibility = View.GONE
        } else {
            binding.rcvCartItems.visibility = View.GONE
            binding.tvCartEmpty.visibility = View.VISIBLE
        }

        binding.btnOrderMedicine.setOnClickListener {
            if (selectedMedicineList.size > 0) {
                showOrderMedicineDialog()
            } else {
                Utils.toastMessage(
                    requireContext(), getString(R.string.please_select_some_medicines)
                )
            }
        }

    }

    private fun showOrderMedicineDialog() {
        dialogBinding = DialogOrderMedicineBinding.inflate(layoutInflater)

        val dialog = Dialog(requireContext(), R.style.Theme_HealthCareApp_RoundedCornersDialog)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(true)

        val personalContact = PreferenceManager.getSharedPreferences(
            requireContext(), Constants.PREF_PERSONAL_CONTACT
        )
        if (personalContact != null) {
            if (personalContact.isEmpty()) dialogBinding.tvContact.text =
                getString(R.string.add_your_personal_contact)
            else dialogBinding.tvContact.text = personalContact
        }

        val deliveryAddress = PreferenceManager.getSharedPreferences(
            requireContext(), Constants.PREF_USER_ADDRESS
        )
        if (deliveryAddress != null) {
            if (deliveryAddress.isEmpty()) dialogBinding.tvAddress.text =
                getString(R.string.add_your_location_address)
            else dialogBinding.tvAddress.text = deliveryAddress
        }

        val email = PreferenceManager.getSharedPreferences(
            requireContext(), Constants.PREF_EMAIL
        )
        if (email != null) {
            if (email.isEmpty()) dialogBinding.tvEmail.text =
                getString(R.string.add_your_location_address)
            else dialogBinding.tvEmail.text = email
        }

        dialogBinding.tvMedicineCount.text = cartCount.toString()

        var totalAmount = 0

        for (element in selectedMedicineList) {
            totalAmount += element.price * element.quantity
        }

        dialogBinding.tvPrice.text = "${Constants.rupeeSymbol} $totalAmount"

        dialogBinding.btnOrder.setOnClickListener {
            if (deliveryAddress?.isEmpty() == true || personalContact?.isEmpty() == true) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.please_add_both_delivery_address_contact),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val db = Database(requireContext(), Constants.DB_NAME, null, 1)

                val orderMedicineEntity = OrderMedicineEntity(
                    null,
                    PreferenceManager.getSharedPreferencesIntValues(
                        requireContext(), Constants.PREF_USER_ID
                    ),
                    PreferenceManager.getSharedPreferences(
                        requireContext(), Constants.PREF_USERNAME
                    ).toString(),
                    personalContact,
                    deliveryAddress,
                    email,
                    "Cash On Delivery",
                    totalAmount,
                    cartCount,
                    Gson().toJson(selectedMedicineList)
                )

                GlobalScope.launch(Dispatchers.IO) {
                    appDatabase.orderMedicineDao().bookOrder(orderMedicineEntity)
                }

                PreferenceManager.clearSharedPreference(
                    requireContext(), Constants.PREF_CART_MEDICINE_IDS_KEYS
                )
                PreferenceManager.clearSharedPreference(
                    requireContext(), Constants.PREF_CART_MEDICINE_QUANTITY_VALUES
                )
                selectedMedicineList.clear()
                cartCount = 0
                setCartCount()
                adapter.notifyDataSetChanged()
                Utils.toastMessage(
                    requireContext(), getString(R.string.order_booked_successfully)
                )
                findNavController().navigateUp()
            }
            dialog.dismiss()
        }

        if (!dialog.isShowing) dialog.show()
    }
}