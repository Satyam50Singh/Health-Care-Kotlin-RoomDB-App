package satya.app.healthcareapproomdb.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import satya.app.healthcareapproomdb.adapters.AmbulanceAdapter
import satya.app.healthcareapproomdb.databinding.FragmentFindAmbulanceBinding
import satya.app.healthcareapproomdb.listeners.CommonItemListClickListener
import satya.app.healthcareapproomdb.models.AmbulanceModel
import java.util.*

class FindAmbulanceFragment : Fragment(), CommonItemListClickListener<AmbulanceModel> {

    private lateinit var binding: FragmentFindAmbulanceBinding
    private val ambulanceList = ArrayList<AmbulanceModel>()
    private lateinit var adapter: AmbulanceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFindAmbulanceBinding.inflate(layoutInflater, container, false)

        initUI()

        return binding.root
    }

    private fun initUI() {
        for (i in 1..20) {
            val type = if (i % 2 == 0)
                "Regular"
            else if (i % 3 == 0)
                "Ventilator"
            else
                "Fully AC"

            ambulanceList.add(
                AmbulanceModel(
                    100 + i,
                    "Ambulance AMB2023[$i]",
                    type,
                    "8th Floor, TAG Tower, Plot No. 17-18, Udhyog Vihar, Phase - 4, Gurugram, Haryana - 122015, India"
                )
            )
        }

        binding.svSearchAmbulance.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                setFilteredList(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })

        adapter = AmbulanceAdapter(requireContext(), ambulanceList, this)
        binding.rcvAmbulance.adapter = adapter
    }

    private fun setFilteredList(newText: String?) {
        val filteredList = ArrayList<AmbulanceModel>()
        for (listItem in ambulanceList) {
            if (listItem.name.lowercase(Locale.ROOT).contains(
                    newText?.lowercase(Locale.ROOT).toString()
                ) || listItem.type.lowercase(Locale.ROOT)
                    .contains(newText?.lowercase(Locale.ROOT).toString())
            ) {
                filteredList.add(listItem)
            }
        }
        if (filteredList.size > 0) adapter.setFilteredList(filteredList)
    }

    override fun onItemClick(item: AmbulanceModel) {
        val action =
            FindAmbulanceFragmentDirections.actionNavFindAmbulanceFragmentToBookAnAmbulanceFragment(
                item
            )
        findNavController().navigate(action)
    }
}