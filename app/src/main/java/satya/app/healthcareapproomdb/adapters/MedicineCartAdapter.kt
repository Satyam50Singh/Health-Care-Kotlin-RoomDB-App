package satya.app.healthcareapproomdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import satya.app.healthcareapproomdb.databinding.ItemCartMedicineBinding
import satya.app.healthcareapproomdb.models.MedicineModel
import satya.app.healthcareapproomdb.view.fragments.OrderMedicineFragment.Companion.cartCount

class MedicineCartAdapter(
    val context: Context,
    private val cartListItem: ArrayList<MedicineModel>
) : RecyclerView.Adapter<MedicineCartAdapter.ViewHolder>() {

    class ViewHolder(val itemCartMedicineBinding: ItemCartMedicineBinding) :
        RecyclerView.ViewHolder(itemCartMedicineBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCartMedicineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartListItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(cartListItem[position]) {
                itemCartMedicineBinding.tvMedicineType.text = medicineType
                itemCartMedicineBinding.tvMedicineName.text =  "$medicineName ($weight)"
                itemCartMedicineBinding.tvMedicineName.isSelected = true
                itemCartMedicineBinding.tvMedicinePrice.text = "$piecePerPack pc/\u20B9$price"
                itemCartMedicineBinding.tvQuantity.text = quantity.toString()

                itemCartMedicineBinding.tvRemoveFromCart.setOnClickListener {
                    cartCount-=1
                    cartListItem.remove(cartListItem[position])
                    notifyDataSetChanged()
                }
                itemCartMedicineBinding.tvPlusQuantity.setOnClickListener {
                    if (quantity < 10) {
                        quantity += 1
                        itemCartMedicineBinding.tvQuantity.text = quantity.toString()
                    }
                }
                itemCartMedicineBinding.tvMinusQuantity.setOnClickListener {
                    if (quantity > 1) {
                        quantity -= 1
                        itemCartMedicineBinding.tvQuantity.text = quantity.toString()
                    } else {
                        cartCount-=1
                        cartListItem.remove(cartListItem[position])
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }
}