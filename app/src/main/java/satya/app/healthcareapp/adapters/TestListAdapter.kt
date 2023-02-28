package satya.app.healthcareapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import satya.app.healthcareapp.R
import satya.app.healthcareapp.databinding.ItemTestsListBinding
import satya.app.healthcareapp.listeners.CommonClickListener
import satya.app.healthcareapp.listeners.CommonItemListClickListener
import satya.app.healthcareapp.models.LabTestModel


class TestListAdapter(
    val context: Context,
    private var testList: ArrayList<LabTestModel>,
    private val commonClickListener: CommonItemListClickListener<LabTestModel>
) : RecyclerView.Adapter<TestListAdapter.ViewHolder>() {

    class ViewHolder(val itemTestsListBinding: ItemTestsListBinding) :
        RecyclerView.ViewHolder(itemTestsListBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTestsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return testList.size
    }

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var expand: Boolean

        with(holder) {
            with(testList[position]) {
                itemTestsListBinding.tvTestName.text = testTitle
                itemTestsListBinding.tvTestDescription.text = description
                itemTestsListBinding.tvSampleType.text = sampleType
                itemTestsListBinding.tvTubeType.text = tubeType

                if (fastingRequired) {
                    itemTestsListBinding.tvFastingRequired.text =
                        context.getString(R.string.required)
                } else {
                    itemTestsListBinding.tvFastingRequired.text =
                        context.getString(R.string.not_required)
                }

                itemTestsListBinding.tvTestName.setOnClickListener {
                    isExpandable = !isExpandable
                    notifyItemChanged(adapterPosition)
                }

                expand = testList[position].isExpandable

                if (expand) {
                    itemTestsListBinding.llExpandDetailsView.visibility = View.VISIBLE
                    itemTestsListBinding.llExpandDetailsView.alpha = 1.0f
                } else {
                    itemTestsListBinding.llExpandDetailsView.visibility = View.GONE
                    itemTestsListBinding.llExpandDetailsView.alpha = 0.0f
                }

                itemTestsListBinding.ibFindLabs.setOnClickListener {
                    commonClickListener.onItemClick(testList[adapterPosition])
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(filteredList: ArrayList<LabTestModel>) {
        testList = filteredList
        notifyDataSetChanged()
    }
}