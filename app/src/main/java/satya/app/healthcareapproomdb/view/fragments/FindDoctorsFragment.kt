package satya.app.healthcareapproomdb.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import satya.app.healthcareapproomdb.adapters.FindDoctorsAdapter
import satya.app.healthcareapproomdb.databinding.FragmentFindDoctorsBinding
import satya.app.healthcareapproomdb.listeners.CommonItemListClickListener
import satya.app.healthcareapproomdb.models.DashboardModel
import satya.app.healthcareapproomdb.utils.Utils
import java.util.*

class FindDoctorsFragment : Fragment(), CommonItemListClickListener<DashboardModel> {

    private lateinit var binding: FragmentFindDoctorsBinding
    private lateinit var doctorsList: ArrayList<DashboardModel>
    private lateinit var adapter: FindDoctorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFindDoctorsBinding.inflate(layoutInflater, container, false)

        initUi()
        return binding.root
    }

    private fun initUi() {

        binding.svSearchDrType.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                setFilteredList(newText)
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })

        doctorsList = Utils.getDoctorTypesList(requireContext())
        adapter = FindDoctorsAdapter(requireContext(), doctorsList, this)
        binding.rcvDoctors.adapter = adapter
    }

    private fun setFilteredList(newText: String?) {
        val filteredList = ArrayList<DashboardModel>()
        for (listItem in doctorsList) {
            if (listItem.title.lowercase(Locale.ROOT)
                    .contains(newText?.lowercase(Locale.ROOT).toString())
            ) {
                filteredList.add(listItem)
            }
        }

        if (filteredList.size > 0)
            adapter.setFilteredList(filteredList)

    }

    override fun onItemClick(item: DashboardModel) {
        val action = FindDoctorsFragmentDirections.actionNavFindDoctorsFragmentToDoctorsListFragment(item.title)
        findNavController().navigate(action)
    }
}