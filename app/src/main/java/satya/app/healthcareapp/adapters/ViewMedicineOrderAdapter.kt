package satya.app.healthcareapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import satya.app.healthcareapp.databinding.ItemViewMedicineOrderBinding
import satya.app.healthcareapp.models.MedicineModel
import satya.app.healthcareapp.models.OrderMedicineModel
import satya.app.healthcareapp.utils.Constants.Companion.rupeeSymbol

class ViewMedicineOrderAdapter(
    val context: Context,
    private val records: ArrayList<OrderMedicineModel>
) : RecyclerView.Adapter<ViewMedicineOrderAdapter.ViewHolder>() {

    private lateinit var adapter: InnerMedicineOrderAdapter

    class ViewHolder(val itemViewMedicineOrderBinding: ItemViewMedicineOrderBinding) :
        RecyclerView.ViewHolder(itemViewMedicineOrderBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemViewMedicineOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return records.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var expand: Boolean
        val type = object : TypeToken<ArrayList<MedicineModel?>?>() {}.type

        with(holder) {
            with(records[position]) {
                itemViewMedicineOrderBinding.tvContact.text = mobile
                itemViewMedicineOrderBinding.tvEmail.text = userEmail
                itemViewMedicineOrderBinding.tvAddress.text = deliveryAddress
                itemViewMedicineOrderBinding.tvMedicineCount.text = itemCount.toString()
                itemViewMedicineOrderBinding.tvPrice.text = "$rupeeSymbol $totalAmount"

                itemViewMedicineOrderBinding.ivExpandMedicines.setOnClickListener {
                    isExpandable = !isExpandable
                    notifyItemChanged(adapterPosition)
                }

                expand = records[position].isExpandable

                if (expand) {

                    val medicineRecord: ArrayList<MedicineModel> =
                        Gson().fromJson(medicineList, type)
                    adapter = InnerMedicineOrderAdapter(context, medicineRecord)
                    itemViewMedicineOrderBinding.rcvMedicines.adapter = adapter

                    itemViewMedicineOrderBinding.rcvMedicines.visibility = View.VISIBLE
                    itemViewMedicineOrderBinding.rcvMedicines.alpha = 1.0f
                } else {
                    itemViewMedicineOrderBinding.rcvMedicines.visibility = View.GONE
                    itemViewMedicineOrderBinding.rcvMedicines.alpha = 0.0f
                }
            }
        }
    }
}