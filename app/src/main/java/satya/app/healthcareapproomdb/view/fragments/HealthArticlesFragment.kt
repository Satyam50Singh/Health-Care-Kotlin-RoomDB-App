package satya.app.healthcareapproomdb.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import satya.app.healthcareapproomdb.R
import satya.app.healthcareapproomdb.adapters.HealthArticleAdapter
import satya.app.healthcareapproomdb.databinding.FragmentHealthArticlesBinding
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.db.entities.HealthArticleEntity
import satya.app.healthcareapproomdb.listeners.CommonItemListClickListener
import satya.app.healthcareapproomdb.utils.Utils

class HealthArticlesFragment : Fragment(), CommonItemListClickListener<HealthArticleEntity> {

    private lateinit var binding: FragmentHealthArticlesBinding
    private lateinit var adapter: HealthArticleAdapter
    private var healthArticleList = ArrayList<HealthArticleEntity>()
    private lateinit var appDatabase: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHealthArticlesBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initUI() {
        healthArticleList = Utils.getHealthArticles()
        appDatabase = AppDatabase.getDatabase(requireContext())
        var healthArticles: ArrayList<HealthArticleEntity>

        GlobalScope.launch {
            healthArticles =
                appDatabase.healthArticleDao().getAllArticles() as ArrayList<HealthArticleEntity>
            setArticleAdapter(healthArticles)
        }

        binding.fabAddArticles.setOnClickListener {
            findNavController().navigate(R.id.action_nav_healthArticlesFragment_to_addHealthArticleFragment)
        }

    }

    private fun setArticleAdapter(healthArticles: java.util.ArrayList<HealthArticleEntity>) {
        for (articles in healthArticleList) {
            healthArticles.add(articles)
        }
        adapter = HealthArticleAdapter(requireContext(), healthArticles, this)
        binding.rcvHealthArticles.adapter = adapter
    }

    override fun onItemClick(item: HealthArticleEntity) {
        val action =
            HealthArticlesFragmentDirections.actionNavHealthArticlesFragmentToViewHealthArticleFragment(
                item.title,
                item
            )
        findNavController().navigate(action)
    }
}