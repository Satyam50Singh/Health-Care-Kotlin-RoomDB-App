package satya.app.healthcareapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import satya.app.healthcareapp.databinding.ItemViewLabTestBookingBinding
import satya.app.healthcareapp.listeners.CommonItemListClickListener
import satya.app.healthcareapp.models.BookLabTestModel
import satya.app.healthcareapp.utils.Constants.Companion.rupeeSymbol

class ViewLabTestAppointmentsAdapter(
    private var records: ArrayList<BookLabTestModel>,
    private val commonItemListClickListener: CommonItemListClickListener<BookLabTestModel>
) : RecyclerView.Adapter<ViewLabTestAppointmentsAdapter.ViewHolder>() {

    class ViewHolder(val itemViewLabTestAppointmentsBinding: ItemViewLabTestBookingBinding) :
        RecyclerView.ViewHolder(itemViewLabTestAppointmentsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewLabTestBookingBinding.inflate(
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
                itemViewLabTestAppointmentsBinding.tvTestName.text = testTitle
                itemViewLabTestAppointmentsBinding.tvLabName.text = labTitle
                itemViewLabTestAppointmentsBinding.tvVisit.text = visitType
                itemViewLabTestAppointmentsBinding.tvAmountPaid.text = "$rupeeSymbol $price"
                if (visitType == "Home") {
                    itemViewLabTestAppointmentsBinding.tvHomeAddress.visibility = View.VISIBLE
                    itemViewLabTestAppointmentsBinding.tvHomeAddress.text = homeAddress
                } else if (visitType == "Lab") {
                    itemViewLabTestAppointmentsBinding.tvLabAddress.visibility = View.VISIBLE
                    itemViewLabTestAppointmentsBinding.tvLabAddress.text = labAddress
                }

                itemViewLabTestAppointmentsBinding.root.setOnClickListener {
                    commonItemListClickListener.onItemClick(records[adapterPosition])
                }
            }
        }
    }
}