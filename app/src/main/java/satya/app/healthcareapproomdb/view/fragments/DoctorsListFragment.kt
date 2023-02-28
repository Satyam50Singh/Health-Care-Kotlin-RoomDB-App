package satya.app.healthcareapproomdb.view.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import satya.app.healthcareapproomdb.R
import satya.app.healthcareapproomdb.adapters.DoctorListAdapter
import satya.app.healthcareapproomdb.databinding.FragmentDoctorsListBinding
import satya.app.healthcareapproomdb.listeners.CommonItemListClickListener
import satya.app.healthcareapproomdb.listeners.OnDoctorItemClickListener
import satya.app.healthcareapproomdb.models.DoctorListModel
import satya.app.healthcareapproomdb.utils.Utils
import java.util.*


class DoctorsListFragment : Fragment(), CommonItemListClickListener<DoctorListModel>,
    OnDoctorItemClickListener {

    private lateinit var binding: FragmentDoctorsListBinding
    private lateinit var category: String
    private lateinit var doctorsList: ArrayList<DoctorListModel>
    private lateinit var adapter: DoctorListAdapter

    companion object {
        val REQUIRED_LOCATION_PERMISSION = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        category = DoctorsListFragmentArgs.fromBundle(requireArguments()).category
        Log.e("TAG", "onCreateView: $category")
        binding = FragmentDoctorsListBinding.inflate(layoutInflater, container, false)

        initUi()
        return binding.root
    }


    private fun initUi() {

        binding.svSearchDoctor.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                setFilteredList(newText)
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })

        doctorsList =
            if (category == "Physicians") Utils.getPhysiciansList() else Utils.getDoctorsList()

        adapter = DoctorListAdapter(requireContext(), doctorsList, this, this)
        binding.rcvDoctorList.adapter = adapter
    }


    private fun setFilteredList(newText: String?) {
        val filteredList = ArrayList<DoctorListModel>()
        for (listItem in doctorsList) {
            if (listItem.name.lowercase(Locale.ROOT)
                    .contains(newText?.lowercase(Locale.ROOT).toString())
            ) {
                filteredList.add(listItem)
            }
        }

        if (filteredList.size > 0) adapter.setFilteredList(filteredList)
    }


    private val callPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Do if the permission is granted
            Snackbar.make(
                binding.rcvDoctorList,
                getString(R.string.thank_you_for_allowing),
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            // Do otherwise
            Snackbar.make(
                binding.rcvDoctorList,
                getString(R.string.call_permission_is_required_to_make_a_call),
                Snackbar.LENGTH_LONG
            ).show()

        }
    }

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isGranted = permissions.entries.all {
            it.value
        }
        if (isGranted) {
            Snackbar.make(
                binding.rcvDoctorList,
                getString(R.string.thank_you_for_allowing),
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            // Do otherwise
            Snackbar.make(
                binding.rcvDoctorList,
                getString(R.string.location_permission_is_required_to_open_map),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun onClick(itemDoctor: DoctorListModel, btnType: String) {
        when (btnType) {
            getString(R.string.call) -> {
                // check call permission
                when {
                    ContextCompat.checkSelfPermission(
                        requireContext(), Manifest.permission.CALL_PHONE
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        val callIntent = Intent(Intent.ACTION_CALL)
                        callIntent.data = Uri.parse("tel:${itemDoctor.phone}")
                        startActivity(callIntent)
                    }
                    shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE) -> {
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setMessage(
                            getString(R.string.please_allow_permission)
                        ).setPositiveButton(getString(R.string.dialog_ok)) { _, _ ->
                            callPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                        }.setNegativeButton(getString(R.string.dialog_cancel)) { dialog, _ ->
                            // User cancelled the dialog
                            dialog.cancel()
                        }
                        // Create the AlertDialog object and return it
                        val alertDialog = builder.create()
                        if (!alertDialog.isShowing) alertDialog.show()
                    }
                    else -> callPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                }
            }
            getString(R.string.location) -> {
                when {
                    (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED) -> {
                        // Permission Granted
                        val action =
                            DoctorsListFragmentDirections.actionDoctorsListFragmentToMapFragment(
                                itemDoctor, itemDoctor.name
                            )
                        findNavController().navigate(action)
                    }
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                        // Permission Denied
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setMessage(
                            getString(R.string.please_allow_permission)
                        ).setPositiveButton(getString(R.string.dialog_ok)) { _, _ ->
                            locationPermissionLauncher.launch(REQUIRED_LOCATION_PERMISSION)
                        }.setNegativeButton(getString(R.string.dialog_cancel)) { dialog, _ ->
                            // User cancelled the dialog
                            dialog.cancel()
                        }
                        // Create the AlertDialog object and return it
                        val alertDialog = builder.create()
                        if (!alertDialog.isShowing) alertDialog.show()
                    }
                    else -> {
                        locationPermissionLauncher.launch(REQUIRED_LOCATION_PERMISSION)
                    }
                }
            }
        }
    }

    override fun onItemClick(item: DoctorListModel) {
        val action =
            DoctorsListFragmentDirections.actionDoctorsListFragmentToViewDoctorDetailsFragment(item)
        findNavController().navigate(action)
    }
}