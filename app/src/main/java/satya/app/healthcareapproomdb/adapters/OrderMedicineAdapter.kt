package satya.app.healthcareapproomdb.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import satya.app.healthcareapproomdb.R
import satya.app.healthcareapproomdb.databinding.ItemMedicineBinding
import satya.app.healthcareapproomdb.models.MedicineModel
import satya.app.healthcareapproomdb.utils.Constants.Companion.PREF_CART_MEDICINE_IDS_KEYS
import satya.app.healthcareapproomdb.utils.Constants.Companion.PREF_CART_MEDICINE_QUANTITY_VALUES
import satya.app.healthcareapproomdb.utils.Constants.Companion.rupeeSymbol
import satya.app.healthcareapproomdb.utils.PreferenceManager
import satya.app.healthcareapproomdb.utils.Utils
import satya.app.healthcareapproomdb.view.fragments.OrderMedicineFragment.Companion.cartCount
import satya.app.healthcareapproomdb.view.fragments.OrderMedicineFragment.Companion.setCartCount

class OrderMedicineAdapter(
    val context: Context,
    private var medicineList: ArrayList<MedicineModel>,
) : RecyclerView.Adapter<OrderMedicineAdapter.ViewHolder>() {

    companion object {
        val selectedMedicineList = ArrayList<MedicineModel>()
        val selectedMedicineIdsList = mutableMapOf<Int, Int>()
    }

    class ViewHolder(val itemMedicineBinding: ItemMedicineBinding) :
        RecyclerView.ViewHolder(itemMedicineBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMedicineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            with(medicineList[position]) {
                itemMedicineBinding.tvMedicineType.text = medicineType
                itemMedicineBinding.tvMedicineName.text = "$medicineName ($weight)"
                itemMedicineBinding.tvMedicinePrice.text = "$piecePerPack pc/$rupeeSymbol$price"

                // show quantity of selected cart medicine
                if (quantity > 0) {
                    itemMedicineBinding.tvAddToCart.visibility = View.GONE
                    itemMedicineBinding.tvQuantity.visibility = View.VISIBLE
                    itemMedicineBinding.tvPlusQuantity.visibility = View.VISIBLE
                    itemMedicineBinding.tvMinusQuantity.visibility = View.VISIBLE
                    itemMedicineBinding.tvQuantity.text = quantity.toString()
                }

                itemMedicineBinding.tvAddToCart.setOnClickListener {
                    itemMedicineBinding.tvAddToCart.visibility = View.GONE
                    itemMedicineBinding.tvQuantity.visibility = View.VISIBLE
                    itemMedicineBinding.tvPlusQuantity.visibility = View.VISIBLE
                    itemMedicineBinding.tvMinusQuantity.visibility = View.VISIBLE
                    quantity += 1
                    itemMedicineBinding.tvQuantity.text = quantity.toString()
                    selectedMedicineList.add(medicineList[adapterPosition])
                    selectedMedicineIdsList[medicineId] = quantity

                    Log.e("TAG", "onBindViewHolder: $selectedMedicineIdsList")
                    PreferenceManager.setSharedPreferences(
                        context,
                        PREF_CART_MEDICINE_IDS_KEYS,
                        selectedMedicineIdsList.keys.toString()
                    )
                    PreferenceManager.setSharedPreferences(
                        context,
                        PREF_CART_MEDICINE_QUANTITY_VALUES,
                        selectedMedicineIdsList.values.toString()
                    )

                    cartCount += 1
                    setCartCount()
                }

                itemMedicineBinding.tvPlusQuantity.setOnClickListener {
                    if (quantity < 10) {
                        selectedMedicineList.remove(medicineList[adapterPosition])
                        quantity += 1
                        itemMedicineBinding.tvQuantity.text = quantity.toString()
                        selectedMedicineList.add(medicineList[adapterPosition])
                        selectedMedicineIdsList[medicineId] = quantity
                    } else {
                        Utils.toastMessage(
                            context, context.getString(R.string.you_cannot_add_more_than_10)
                        )
                    }
                }
                itemMedicineBinding.tvMinusQuantity.setOnClickListener {
                    if (quantity >= 1) {
                        selectedMedicineList.remove(medicineList[adapterPosition])
                        quantity -= 1
                        itemMedicineBinding.tvQuantity.text = quantity.toString()
                        selectedMedicineList.add(medicineList[adapterPosition])
                        if (quantity == 0) {
                            cartCount -= 1
                            setCartCount()
                            selectedMedicineList.remove(medicineList[adapterPosition])
                            selectedMedicineIdsList.remove(medicineId)
                            itemMedicineBinding.tvAddToCart.visibility = View.VISIBLE
                            itemMedicineBinding.tvQuantity.visibility = View.GONE
                            itemMedicineBinding.tvPlusQuantity.visibility = View.GONE
                            itemMedicineBinding.tvMinusQuantity.visibility = View.GONE
                        }
                    }
                }

            }
        }
    }

    fun setFilteredList(filteredList: ArrayList<MedicineModel>) {
        medicineList = filteredList
        notifyDataSetChanged()
    }
}