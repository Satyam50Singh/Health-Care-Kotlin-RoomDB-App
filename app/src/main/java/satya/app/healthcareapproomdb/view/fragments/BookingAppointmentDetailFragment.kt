package satya.app.healthcareapproomdb.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import satya.app.healthcareapproomdb.adapters.TabLayoutAdapter
import satya.app.healthcareapproomdb.databinding.FragmentBookingAppointmentDetailBinding

class BookingAppointmentDetailFragment : Fragment() {
    private lateinit var binding: FragmentBookingAppointmentDetailBinding
    private lateinit var adapter: TabLayoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookingAppointmentDetailBinding.inflate(layoutInflater, container, false)

        setTabLayout()
        return binding.root
    }

    private fun setTabLayout() {
        adapter = TabLayoutAdapter(childFragmentManager, lifecycle)

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Doctors"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Ambulance"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Lab Test"))

        binding.viewPager.adapter = adapter

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }
}