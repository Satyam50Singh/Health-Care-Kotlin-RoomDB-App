package satya.app.healthcareapp.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import satya.app.healthcareapp.R
import satya.app.healthcareapp.databinding.ItemDoctorListBinding
import satya.app.healthcareapp.listeners.CommonItemListClickListener
import satya.app.healthcareapp.listeners.OnDoctorItemClickListener
import satya.app.healthcareapp.models.DoctorListModel
import satya.app.healthcareapp.view.activites.DashboardActivity
import satya.app.healthcareapp.view.fragments.MapFragment

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