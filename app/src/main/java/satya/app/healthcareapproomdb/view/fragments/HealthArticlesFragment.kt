package satya.app.healthcareapproomdb.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import satya.app.healthcareapproomdb.R
import satya.app.healthcareapproomdb.adapters.HealthArticleAdapter
import satya.app.healthcareapproomdb.databinding.FragmentHealthArticlesBinding
import satya.app.healthcareapproomdb.db.Database
import satya.app.healthcareapproomdb.db.entities.HealthArticleEntity
import satya.app.healthcareapproomdb.listeners.CommonItemListClickListener
import satya.app.healthcareapproomdb.utils.Utils

class HealthArticlesFragment : Fragment(), CommonItemListClickListener<HealthArticleEntity> {

    private lateinit var binding: FragmentHealthArticlesBinding
    private lateinit var adapter: HealthArticleAdapter
    private var healthArticleList = ArrayList<HealthArticleEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHealthArticlesBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    private fun initUI() {
        healthArticleList = Utils.getHealthArticles()

        val db = Database(requireContext(), "healthcare", null, 1)
        val cursor = db.getHealthArticles()

        val healthArticles = ArrayList<HealthArticleEntity>()
        while (cursor?.moveToNext() == true) {
            val articleModel = HealthArticleEntity(
                cursor.getInt(1),
                cursor.getString(3),
                cursor.getString(2),
                cursor.getString(4)
            )
            healthArticles.add(articleModel)
        }

        for (articles in healthArticleList) {
            healthArticles.add(articles)
        }

        binding.fabAddArticles.setOnClickListener {
            findNavController().navigate(R.id.action_nav_healthArticlesFragment_to_addHealthArticleFragment)
        }

        adapter = HealthArticleAdapter(requireContext(), healthArticles, this)
        binding.rcvHealthArticles.adapter = adapter
    }

    override fun onItemClick(item: HealthArticleEntity) {
        val action = HealthArticlesFragmentDirections.actionNavHealthArticlesFragmentToViewHealthArticleFragment(item.title, item)
        findNavController().navigate(action)
    }
}