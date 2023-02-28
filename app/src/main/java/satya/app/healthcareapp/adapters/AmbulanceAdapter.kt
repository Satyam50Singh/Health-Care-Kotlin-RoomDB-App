package satya.app.healthcareapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import satya.app.healthcareapp.databinding.ItemAmbulanceListBinding
import satya.app.healthcareapp.listeners.CommonItemListClickListener
import satya.app.healthcareapp.models.AmbulanceModel

class AmbulanceAdapter(
    val context: Context,
    private var ambulanceList: ArrayList<AmbulanceModel>,
    private val commonItemListClickListener: CommonItemListClickListener<AmbulanceModel>
) : RecyclerView.Adapter<AmbulanceAdapter.ViewHolder>() {

    class ViewHolder(val itemAmbulanceBinding: ItemAmbulanceListBinding) :
        RecyclerView.ViewHolder(itemAmbulanceBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAmbulanceListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ambulanceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(ambulanceList[position]) {
                itemAmbulanceBinding.tvAmbulanceName.text = name
                itemAmbulanceBinding.tvAmbulanceType.text = type
                itemAmbulanceBinding.tvAmbulanceAddress.text = address
                itemAmbulanceBinding.root.setOnClickListener {
                    commonItemListClickListener.onItemClick(ambulanceList[adapterPosition])
                }
            }
        }
    }

    fun setFilteredList(filteredList: ArrayList<AmbulanceModel>) {
        ambulanceList = filteredList
        notifyDataSetChanged()
    }

}