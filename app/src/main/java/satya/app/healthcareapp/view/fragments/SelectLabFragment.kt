package satya.app.healthcareapp.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import satya.app.healthcareapp.R
import satya.app.healthcareapp.adapters.LabsListAdapter
import satya.app.healthcareapp.databinding.DialogBookLabTestBinding
import satya.app.healthcareapp.databinding.FragmentSelectLabBinding
import satya.app.healthcareapp.db.Database
import satya.app.healthcareapp.listeners.CommonItemListClickListener
import satya.app.healthcareapp.models.BookLabTestModel
import satya.app.healthcareapp.models.LabListModel
import satya.app.healthcareapp.models.LabTestModel
import satya.app.healthcareapp.utils.Constants
import satya.app.healthcareapp.utils.PreferenceManager
import satya.app.healthcareapp.utils.Utils
import satya.app.healthcareapp.utils.Utils.Companion.getLabsList
import java.util.*

class SelectLabFragment : Fragment(), CommonItemListClickListener<LabListModel> {

    private lateinit var binding: FragmentSelectLabBinding
    private lateinit var selectedTest: LabTestModel
    private lateinit var labsLists: ArrayList<LabListModel>
    private lateinit var dialogBinding: DialogBookLabTestBinding

    private lateinit var adapter: LabsListAdapter
    private val args by navArgs<SelectLabFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        selectedTest = args.selectedTest

        // Inflate the layout for this fragment
        binding = FragmentSelectLabBinding.inflate(layoutInflater, container, false)

        initUi()
        return binding.root
    }

    private fun initUi() {
        binding.svSearchLabs.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                setFilteredList(newText)
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })

        labsLists = getLabsList()
        adapter = LabsListAdapter(requireContext(), labsLists, this)
        binding.rcvLabsList.adapter = adapter
    }

    private fun setFilteredList(newText: String?) {
        val filteredList = ArrayList<LabListModel>()
        for (listItem in labsLists) {
            if (listItem.labTitle.lowercase(Locale.ROOT)
                    .contains(newText?.lowercase(Locale.ROOT).toString())
                || listItem.type.lowercase(Locale.ROOT)
                    .contains(newText?.lowercase(Locale.ROOT).toString())
            ) {
                filteredList.add(listItem)
            }
        }

        if (filteredList.size > 0)
            adapter.setFilteredList(filteredList)
    }

    override fun onItemClick(item: LabListModel) {
        dialogBinding = DialogBookLabTestBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext(), R.style.Theme_HealthCareApp_RoundedCornersDialog)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(true)

        dialogBinding.tvLabName.text = item.labTitle
        dialogBinding.tvLabType.text = item.type
        dialogBinding.tvLabAddress.text = item.address
        dialogBinding.tvLabPrice.text = "\u20B9 ${item.price}"
        dialogBinding.llHomeVisitAddress.visibility = View.GONE

        dialogBinding.switchLabVisit.setOnClickListener {
            if (dialogBinding.switchLabVisit.isChecked) {
                dialogBinding.tvLabPrice.text =
                    "\u20B9 ${item.price + item.homeVisitCharges}"
                dialogBinding.llHomeVisitAddress.visibility = View.VISIBLE
            } else {
                dialogBinding.llHomeVisitAddress.visibility = View.GONE
                dialogBinding.tvLabPrice.text = "\u20B9 ${item.price}"
            }
        }

        dialogBinding.btnBookLabTest.setOnClickListener {
            if (validateControls()) {
                val db = Database(requireContext(), "healthcare", null, 1)
                val result = db.bookLabTest(
                    BookLabTestModel(
                        -1,
                        PreferenceManager.getSharedPreferencesIntValues(requireContext(), Constants.PREF_USER_ID),
                        selectedTest.testTitle,
                        PreferenceManager.getSharedPreferences(
                            requireContext(), Constants.PREF_USERNAME
                        ).toString(),
                        dialogBinding.etPhone.text.toString(),
                        item.labTitle,
                        item.address,
                        if (dialogBinding.switchLabVisit.isChecked) (item.price + item.homeVisitCharges) else item.price,
                        if (dialogBinding.switchLabVisit.isChecked) "Home" else "Lab",
                        dialogBinding.etAddress.text.toString() + ", " + dialogBinding.etCity.text.toString() + ", " + dialogBinding.etPincode.text.toString()
                    )
                )

                if (result) {
                    Utils.toastMessage(
                        requireContext(),
                        getString(R.string.lab_booked_successfully)
                    )
                    dialog.dismiss()
                } else {
                    Utils.toastMessage(
                        requireContext(), getString(R.string.something_wrong_happened)
                    )
                }
            }
        }

        dialog.show()
    }

    private fun validateControls(): Boolean {
        if (dialogBinding.switchLabVisit.isChecked) {
            with(dialogBinding) {
                if (etAddress.text.isEmpty()) {
                    Utils.toastMessage(
                        requireContext(), getString(R.string.please_enter_address)
                    )
                    return false
                } else if (etCity.text.isEmpty()) {
                    Utils.toastMessage(requireContext(), getString(R.string.please_enter_city))
                    return false
                } else if (etPincode.text.isEmpty()) {
                    Utils.toastMessage(
                        requireContext(), getString(R.string.please_enter_pin_code)
                    )
                    return false
                }
            }
        }
        if (dialogBinding.etPhone.text.isEmpty()) {
            Utils.toastMessage(
                requireContext(), getString(R.string.please_enter_mobile_number)
            )
            return false
        }
        return true
    }
}