package satya.app.healthcareapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import satya.app.healthcareapp.databinding.FragmentViewHealthArticleBinding

class ViewHealthArticleFragment : Fragment() {

    private lateinit var binding: FragmentViewHealthArticleBinding

    private val args by navArgs<ViewHealthArticleFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewHealthArticleBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    private fun initUI() {
        val articleDetails = args.selectedArticle
        binding.tvArticleHeading.text = articleDetails.title
        binding.tvArticlePostedOn.text = articleDetails.uploadedOn
        binding.tvArticleDetails.text = articleDetails.article
    }
}