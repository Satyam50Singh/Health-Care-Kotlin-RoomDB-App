package satya.app.healthcareapproomdb.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import satya.app.healthcareapproomdb.R
import satya.app.healthcareapproomdb.adapters.DashboardAdapter
import satya.app.healthcareapproomdb.databinding.FragmentDashboardBinding
import satya.app.healthcareapproomdb.listeners.OnItemClickListener
import satya.app.healthcareapproomdb.models.DashboardModel
import satya.app.healthcareapproomdb.utils.Utils

class DashboardFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var dashboardListItems: ArrayList<DashboardModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setImageSlider()

        setDashboardModuleList()
    }

    private fun setImageSlider() {
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.health_care_banner, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner_1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner_2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner_3, ScaleTypes.FIT))

        binding.imageSliderDashboard.setImageList(imageList)
    }

    private fun setDashboardModuleList() {
        dashboardListItems = Utils.getDashboardModuleList(requireActivity())
        binding.rcvDashboard.adapter = DashboardAdapter(requireActivity(), dashboardListItems, this)
    }

    override fun onClick(postion: Int, view: View) {
        val navController = findNavController()
        when (dashboardListItems[postion].title) {
            getString(R.string.find_doctors) -> {
                navController.navigate(R.id.action_nav_dashboardFragment_to_findDoctorsFragment)
            }
            getString(R.string.find_ambulance) -> {
                navController.navigate(R.id.action_nav_dashboardFragment_to_findAmbulanceFragment)
            }
            getString(R.string.book_lab_test) -> {
                navController.navigate(R.id.action_nav_dashboardFragment_to_bookLabTestFragment)
            }
            getString(R.string.order_medicine) -> {
                navController.navigate(R.id.action_nav_dashboardFragment_to_orderMedicineFragment)
            }
            getString(R.string.health_articles) -> {
                navController.navigate(R.id.action_nav_dashboardFragment_to_healthArticlesFragment)
            }
            getString(R.string.action_check_your_appointments) -> {
                navController.navigate(R.id.action_nav_dashboardFragment_to_bookingAppointmentDetailFragment)
            }
        }
    }
}