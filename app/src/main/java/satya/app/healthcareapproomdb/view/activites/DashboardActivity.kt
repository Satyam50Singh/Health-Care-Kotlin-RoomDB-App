package satya.app.healthcareapproomdb.view.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import satya.app.healthcareapproomdb.R
import satya.app.healthcareapproomdb.databinding.ActivityDashboardBinding
import satya.app.healthcareapproomdb.databinding.DrawerHeaderBinding
import satya.app.healthcareapproomdb.utils.Constants
import satya.app.healthcareapproomdb.utils.PreferenceManager
import satya.app.healthcareapproomdb.utils.Utils.userLogout
import satya.app.healthcareapproomdb.view.fragments.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavigation()
    }

    private fun setUpNavigation() {
        setSupportActionBar(binding.toolbarDashboard)

        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_dashboard,
            R.id.nav_profile,
            R.id.nav_find_doctors,
            R.id.nav_find_ambulance,
            R.id.nav_book_lab_test,
            R.id.nav_order_medicines,
            R.id.nav_health_articles,
            R.id.nav_check_your_appointments,
            R.id.nav_orders,
            R.id.nav_settings,
            R.id.nav_logout
        )
            .setOpenableLayout(binding.dlDashboard)
            .build()

        navController = Navigation.findNavController(this, R.id.fragmentContainerView_dashboard)

        NavigationUI.setupActionBarWithNavController(
            this,
            navController,
            appBarConfiguration
        )
        NavigationUI.setupWithNavController(binding.navigationView, navController)

        binding.navigationView.setNavigationItemSelectedListener {
            val id = it.itemId
            if (id == R.id.nav_dashboard) {
                navController.navigate(R.id.nav_dashboard)
            } else
                NavigationUI.onNavDestinationSelected(it, navController)
            binding.dlDashboard.closeDrawer(GravityCompat.START)
            true
        }

        // logout button click
        binding.navigationView.menu[10].setOnMenuItemClickListener {
            if (binding.dlDashboard.isDrawerOpen(GravityCompat.START)) {
                binding.dlDashboard.closeDrawer(GravityCompat.START)
            }
            userLogout(this)
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // setting details of drawer header
        val headerView = binding.navigationView.getHeaderView(0)
        val headerBinding: DrawerHeaderBinding = DrawerHeaderBinding.bind(headerView)
        headerBinding.tvHeaderUsername.text =
            PreferenceManager.getSharedPreferences(this, Constants.PREF_USERNAME)
        headerBinding.tvHeaderEmail.text =
            PreferenceManager.getSharedPreferences(this, Constants.PREF_EMAIL)

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    // code to close the opened drawer
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.dlDashboard.isDrawerOpen(GravityCompat.START)) {
            binding.dlDashboard.closeDrawer(GravityCompat.START)
        } else
            super.onBackPressed()
    }
}