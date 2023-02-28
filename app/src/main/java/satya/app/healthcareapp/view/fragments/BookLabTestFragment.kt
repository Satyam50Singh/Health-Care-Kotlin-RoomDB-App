package satya.app.healthcareapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import satya.app.healthcareapp.adapters.TestListAdapter
import satya.app.healthcareapp.databinding.FragmentBookLabTestBinding
import satya.app.healthcareapp.listeners.CommonItemListClickListener
import satya.app.healthcareapp.models.LabTestModel
import satya.app.healthcareapp.utils.Utils
import java.util.*

class BookLabTestFragment : Fragment(), CommonItemListClickListener<LabTestModel> {

    private lateinit var binding: FragmentBookLabTestBinding
    private lateinit var adapter: TestListAdapter
    private var testList = ArrayList<LabTestModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookLabTestBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    private fun initUI() {
        binding.svSearchTest.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterTestList(newText)
                return true
            }
        })

        testList = Utils.getLabTestList()

        adapter = TestListAdapter(requireContext(), testList, this)
        binding.rcvTestList.adapter = adapter
    }

    private fun filterTestList(newText: String?) {
        val filteredList = ArrayList<LabTestModel>()
        for (listItem in testList) {
            if (listItem.testTitle.toLowerCase(Locale.ROOT)
                    .contains(newText?.toLowerCase(Locale.ROOT).toString())
            ) {
                filteredList.add(listItem)
            }
        }

        if (filteredList.size > 0)
            adapter.setFilteredList(filteredList)

    }

    override fun onItemClick(item: LabTestModel) {
        val action = BookLabTestFragmentDirections.actionNavBookLabTestFragmentToSelectLabFragment(item, item.testTitle)
        findNavController().navigate(action)
    }
}