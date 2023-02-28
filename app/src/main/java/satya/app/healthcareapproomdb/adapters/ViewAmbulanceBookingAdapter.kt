package satya.app.healthcareapproomdb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import satya.app.healthcareapproomdb.databinding.ItemViewAmbulanceBookingBinding
import satya.app.healthcareapproomdb.listeners.CommonItemListClickListener
import satya.app.healthcareapproomdb.models.BookAnAmbulanceModel


class ViewAmbulanceBookingAdapter(
    private var records: ArrayList<BookAnAmbulanceModel>,
    val commonItemListClickListener: CommonItemListClickListener<BookAnAmbulanceModel>
) : RecyclerView.Adapter<ViewAmbulanceBookingAdapter.ViewHolder>() {

    class ViewHolder(val itemViewAmbulanceBookingBinding: ItemViewAmbulanceBookingBinding) :
        RecyclerView.ViewHolder(itemViewAmbulanceBookingBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewAmbulanceBookingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return records.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(records[position]) {
                itemViewAmbulanceBookingBinding.tvAmbulanceName.text = ambulanceName
                itemViewAmbulanceBookingBinding.tvAmbulanceType.text = ambulanceType
                itemViewAmbulanceBookingBinding.tvPickupLocation.text = pickUpLocation
                itemViewAmbulanceBookingBinding.tvDropLocation.text = dropLocation
                itemViewAmbulanceBookingBinding.tvPickupDateTime.text = "Timing : $pickUpDate , $pickUpTime"
                itemViewAmbulanceBookingBinding.tvAmbulanceAddress.text = ambulanceAddress


                itemViewAmbulanceBookingBinding.root.setOnClickListener {
                    commonItemListClickListener.onItemClick(records[adapterPosition])
                }
            }
        }
    }

}