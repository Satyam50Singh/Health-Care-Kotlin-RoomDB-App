package satya.app.healthcareapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import satya.app.healthcareapp.databinding.ItemViewDoctorAppointmentBinding
import satya.app.healthcareapp.listeners.CommonItemListClickListener
import satya.app.healthcareapp.models.BookAnAppointmentModel

class ViewDoctorAppointmentsAdapter(
    context: Context,
    private var records: ArrayList<BookAnAppointmentModel>,
    val commonItemListClickListener: CommonItemListClickListener<BookAnAppointmentModel>
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