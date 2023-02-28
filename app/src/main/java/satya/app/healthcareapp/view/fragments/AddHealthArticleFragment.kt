package satya.app.healthcareapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import satya.app.healthcareapp.R
import satya.app.healthcareapp.databinding.FragmentAddHealthArticleBinding
import satya.app.healthcareapp.db.Database
import satya.app.healthcareapp.models.HealthArticleModel
import satya.app.healthcareapp.utils.Constants
import satya.app.healthcareapp.utils.DateTimeUtils
import satya.app.healthcareapp.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

class AddHealthArticleFragment : Fragment() {

    private lateinit var binding: FragmentAddHealthArticleBinding

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
                val db = Database(requireContext(), "healthcare", null, 1)
                val format = SimpleDateFormat(Constants.dateFormat)
                val convertedDate = DateTimeUtils.getConvertedDate(
                    format.format(Date()), Constants.dateFormat, "MMM.dd.YYYY"
                )

                Log.e("TAG", "initUI: $convertedDate")

                val addHealthArticle = db.addHealthArticle(
                    requireContext(), HealthArticleModel(
                        binding.etArticleHeading.text.toString(),
                        convertedDate,
                        binding.etArticle.text.toString()
                    )
                )

                if (addHealthArticle) {
                    Snackbar.make(
                        it, getString(R.string.article_saved_successfully), Snackbar.LENGTH_LONG
                    ).show()
                    clearControls()
                    findNavController().navigateUp()
                } else {
                    Snackbar.make(
                        it, getString(R.string.something_wrong_happened), Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun clearControls() {
        binding.etArticle.setText("")
        binding.etArticleHeading.setText("")
    }

}