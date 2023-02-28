package satya.app.healthcareapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import satya.app.healthcareapp.R
import satya.app.healthcareapp.adapters.HealthArticleAdapter
import satya.app.healthcareapp.databinding.FragmentHealthArticlesBinding
import satya.app.healthcareapp.db.Database
import satya.app.healthcareapp.listeners.CommonItemListClickListener
import satya.app.healthcareapp.models.HealthArticleModel
import satya.app.healthcareapp.utils.Utils

class HealthArticlesFragment : Fragment(), CommonItemListClickListener<HealthArticleModel> {

    private lateinit var binding: FragmentHealthArticlesBinding
    private lateinit var adapter: HealthArticleAdapter
    private var healthArticleList = ArrayList<HealthArticleModel>()

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

        val healthArticles = ArrayList<HealthArticleModel>()
        while (cursor?.moveToNext() == true) {
            val articleModel = HealthArticleModel(
                cursor.getString(2),
                cursor.getString(1),
                cursor.getString(3)
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

    override fun onItemClick(item: HealthArticleModel) {
        val action = HealthArticlesFragmentDirections.actionNavHealthArticlesFragmentToViewHealthArticleFragment(item, item.title)
        findNavController().navigate(action)
    }
}