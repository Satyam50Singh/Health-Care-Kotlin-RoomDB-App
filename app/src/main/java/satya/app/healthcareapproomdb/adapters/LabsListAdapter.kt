package satya.app.healthcareapproomdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import satya.app.healthcareapproomdb.databinding.ItemLabsListBinding
import satya.app.healthcareapproomdb.listeners.CommonItemListClickListener
import satya.app.healthcareapproomdb.models.LabListModel

class LabsListAdapter(
    val context: Context,
    var labsList: ArrayList<LabListModel>,
    private val commonItemListClickListener: CommonItemListClickListener<LabListModel>
) : RecyclerView.Adapter<LabsListAdapter.ViewHolder>() {

    class ViewHolder(val itemLabsListBinding: ItemLabsListBinding) :
        RecyclerView.ViewHolder(itemLabsListBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLabsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return labsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(labsList[position]) {
                itemLabsListBinding.tvLabTitle.text = labTitle
                itemLabsListBinding.tvLabRating.text = rating.toString()
                itemLabsListBinding.rbRatingBar.rating = rating.toFloat()
                itemLabsListBinding.tvLabNoOfUsers.text = noOfUsers.toString()
                itemLabsListBinding.tvLabType.text = type
                itemLabsListBinding.tvLabAddress.text = address
                itemLabsListBinding.tvLabTime.text = "Timing - $openingTime - $closingTime"
                itemLabsListBinding.root.setOnClickListener {
                    commonItemListClickListener.onItemClick(labsList[adapterPosition])
                }
            }
        }
    }

    fun setFilteredList(filteredList: ArrayList<LabListModel>) {
        labsList = filteredList
        notifyDataSetChanged()
    }
}