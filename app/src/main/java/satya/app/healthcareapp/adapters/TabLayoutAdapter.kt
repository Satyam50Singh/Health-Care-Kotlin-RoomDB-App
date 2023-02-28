package satya.app.healthcareapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import satya.app.healthcareapp.view.fragments.ViewAmbulanceBookingFragment
import satya.app.healthcareapp.view.fragments.ViewDoctorAppointmentsFragment
import satya.app.healthcareapp.view.fragments.ViewLabTestAppointmentsFragment

class TabLayoutAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ViewDoctorAppointmentsFragment()
            1 -> ViewAmbulanceBookingFragment()
            else -> {
                ViewLabTestAppointmentsFragment()
            }
        }
    }
}