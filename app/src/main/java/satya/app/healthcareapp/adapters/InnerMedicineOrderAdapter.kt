package satya.app.healthcareapp.adapters;

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import satya.app.healthcareapp.databinding.ItemMedicineOrderBinding
import satya.app.healthcareapp.models.MedicineModel
import satya.app.healthcareapp.utils.Constants

class InnerMedicineOrderAdapter(
    context: Context,
    val records: ArrayList<MedicineModel>
) : RecyclerView.Adapter<InnerMedicineOrderAdapter.ViewHolder>() {

    class ViewHolder(val itemMedicineOrderBinding: ItemMedicineOrderBinding) :
        RecyclerView.ViewHolder(itemMedicineOrderBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMedicineOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return records.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(records[position]) {
                itemMedicineOrderBinding.tvMedicineType.text = medicineType
                itemMedicineOrderBinding.tvMedicineName.text = medicineName
                itemMedicineOrderBinding.tvMedicinePrice.text = "$piecePerPack pc/${Constants.rupeeSymbol} $price"
            }
        }
    }
}
