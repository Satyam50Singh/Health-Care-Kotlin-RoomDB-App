package satya.app.healthcareapproomdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import satya.app.healthcareapproomdb.R
import satya.app.healthcareapproomdb.databinding.ItemDoctorListBinding
import satya.app.healthcareapproomdb.listeners.CommonItemListClickListener
import satya.app.healthcareapproomdb.listeners.OnDoctorItemClickListener
import satya.app.healthcareapproomdb.models.DoctorListModel

class DoctorListAdapter(
    val context: Context,
    private var dataList: ArrayList<DoctorListModel>,
    private val commonItemListClickListener: CommonItemListClickListener<DoctorListModel>,
    private val onItemClickCallListener: OnDoctorItemClickListener
) : RecyclerView.Adapter<DoctorListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemDoctorListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDoctorListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.tvDoctorName.text = name
                binding.rbRatingBar.numStars = rating
                binding.tvDoctorRating.text = rating.toString()
                binding.ibCallDoctor.setOnClickListener {
                    onItemClickCallListener.onClick(dataList[adapterPosition], context.getString(R.string.call))
                }
                binding.ibDoctorLocation.setOnClickListener {
                    onItemClickCallListener.onClick(dataList[adapterPosition], context.getString(R.string.location))
                }
                binding.ibReadMore.setOnClickListener {
                    commonItemListClickListener.onItemClick(dataList[adapterPosition])
                }
            }
        }
    }

    fun setFilteredList(filteredList: ArrayList<DoctorListModel>) {
        dataList = filteredList
        notifyDataSetChanged()
    }
}