package satya.app.healthcareapproomdb.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import satya.app.healthcareapproomdb.databinding.ItemDoctorsBinding
import satya.app.healthcareapproomdb.listeners.CommonItemListClickListener
import satya.app.healthcareapproomdb.models.DashboardModel

class FindDoctorsAdapter(
    private val context: Context,
    private var doctorsList: ArrayList<DashboardModel>,
    private val commonItemListClickListener: CommonItemListClickListener<DashboardModel>
) :
    RecyclerView.Adapter<FindDoctorsAdapter.ViewHolder>() {

    class ViewHolder(val itemDoctorsBinding: ItemDoctorsBinding) :
        RecyclerView.ViewHolder(itemDoctorsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDoctorsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return doctorsList.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(doctorsList[position]) {
                itemDoctorsBinding.tvDoctorType.text = title
                itemDoctorsBinding.tvDescription.text = description
                itemDoctorsBinding.ivDoctorImage.setImageDrawable(context.getDrawable(imgId))
                itemDoctorsBinding.root.setOnClickListener {
                    commonItemListClickListener.onItemClick(doctorsList[adapterPosition])
                }
            }
        }
    }

    fun setFilteredList(filteredList: ArrayList<DashboardModel>) {
        doctorsList = filteredList
        notifyDataSetChanged()
    }
}