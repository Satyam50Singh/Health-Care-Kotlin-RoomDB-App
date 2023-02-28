package satya.app.healthcareapproomdb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import satya.app.healthcareapproomdb.databinding.ItemViewDoctorAppointmentBinding
import satya.app.healthcareapproomdb.db.entities.BookAnAppointmentEntity
import satya.app.healthcareapproomdb.listeners.CommonItemListClickListener

class ViewDoctorAppointmentsAdapter(
    private var records: List<BookAnAppointmentEntity>,
    private val commonItemListClickListener: CommonItemListClickListener<BookAnAppointmentEntity>
) : RecyclerView.Adapter<ViewDoctorAppointmentsAdapter.ViewHolder>() {

    class ViewHolder(val itemViewDoctorAppointmentsBinding: ItemViewDoctorAppointmentBinding) :
        RecyclerView.ViewHolder(itemViewDoctorAppointmentsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewDoctorAppointmentBinding.inflate(
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
                itemViewDoctorAppointmentsBinding.tvDoctorName.text = doctorName
                itemViewDoctorAppointmentsBinding.tvDoctorCategory.text = doctorCategory
                itemViewDoctorAppointmentsBinding.tvDateTime.text =
                    "Scheduled At : $day, $date, $timeSlot"

                itemViewDoctorAppointmentsBinding.root.setOnClickListener {
                    commonItemListClickListener.onItemClick(records[adapterPosition])
                }
            }
        }
    }
}