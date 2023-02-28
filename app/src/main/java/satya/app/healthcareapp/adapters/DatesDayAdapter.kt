package satya.app.healthcareapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import satya.app.healthcareapp.R
import satya.app.healthcareapp.databinding.ItemDateDaysAppointmentBinding
import satya.app.healthcareapp.listeners.CommonClickListener
import satya.app.healthcareapp.view.fragments.ViewDoctorDetailsFragment

class DatesDayAdapter(
    val context: Context,
    private val next10Dates: MutableMap<String, String>,
    private val commonClickListener: CommonClickListener,
) : RecyclerView.Adapter<DatesDayAdapter.ViewHolder>() {

    class ViewHolder(val itemDateDaysAppointment: ItemDateDaysAppointmentBinding) :
        RecyclerView.ViewHolder(itemDateDaysAppointment.root)

    private val nextDates = ArrayList<String>()
    private val nextDays = ArrayList<String>()
    var columnIndex = -1

    init {
        next10Dates.keys.map {
            nextDates.add(it)
            next10Dates[it]?.let { it1 -> nextDays.add(it1) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDateDaysAppointmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return next10Dates.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemDateDaysAppointment.tvNextDate.text = nextDates[position]
        holder.itemDateDaysAppointment.tvNextDay.text = nextDays[position]
        holder.itemDateDaysAppointment.root.setOnClickListener {
            columnIndex = position
            commonClickListener.onClickItem(columnIndex)
            notifyDataSetChanged()
        }

        if (columnIndex == position) {
            holder.itemDateDaysAppointment.cvDateDay.setCardBackgroundColor(context.getColor(R.color.blue_500))
            holder.itemDateDaysAppointment.tvNextDate.setTextColor(context.getColor(R.color.white))
            holder.itemDateDaysAppointment.tvNextDay.setTextColor(context.getColor(R.color.white))
        } else {
            holder.itemDateDaysAppointment.cvDateDay.setCardBackgroundColor(context.getColor(R.color.white))
            holder.itemDateDaysAppointment.tvNextDate.setTextColor(context.getColor(R.color.black))
            holder.itemDateDaysAppointment.tvNextDay.setTextColor(context.getColor(R.color.black))
        }
    }
}