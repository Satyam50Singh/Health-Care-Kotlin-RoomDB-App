package satya.app.healthcareapproomdb.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import satya.app.healthcareapproomdb.R
import satya.app.healthcareapproomdb.databinding.FragmentAddHealthArticleBinding
import satya.app.healthcareapproomdb.db.AppDatabase
import satya.app.healthcareapproomdb.db.entities.HealthArticleEntity
import satya.app.healthcareapproomdb.utils.Constants
import satya.app.healthcareapproomdb.utils.DateTimeUtils
import satya.app.healthcareapproomdb.utils.Utils
import satya.app.healthcareapproomdb.viewmodels.HealthArticleViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddHealthArticleFragment : Fragment() {

    private lateinit var binding: FragmentAddHealthArticleBinding
    private val viewModel: HealthArticleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddHealthArticleBinding.inflate(
            layoutInflater, container, false
        )

        initUI()
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initUI() {

        binding.btnAddArticle.setOnClickListener {
            if (binding.etArticleHeading.text.isEmpty()) {
                Utils.toastMessage(
                    requireContext(),
                    getString(R.string.please_enter_article_heading)
                )
            } else if (binding.etArticle.text.isEmpty()) {
                Utils.toastMessage(requireContext(), getString(R.string.please_enter_article))
            } else {
                val format = SimpleDateFormat(Constants.dateFormat)
                val convertedDate = DateTimeUtils.getConvertedDate(
                    format.format(Date()), Constants.dateFormat, "MMM.dd.YYYY"
                )

                Log.e("TAG", "initUI: $convertedDate")

                val healthArticleEntity = HealthArticleEntity(
                    null,
                    binding.etArticleHeading.text.toString(),
                    convertedDate,
                    binding.etArticle.text.toString()
                )
                viewModel.addArticle(healthArticleEntity)
                Snackbar.make(
                    it, getString(R.string.article_saved_successfully), Snackbar.LENGTH_LONG
                ).show()
                clearControls()
                findNavController().navigateUp()
            }
        }
    }

    private fun clearControls() {
        binding.etArticle.setText("")
        binding.etArticleHeading.setText("")
    }

}