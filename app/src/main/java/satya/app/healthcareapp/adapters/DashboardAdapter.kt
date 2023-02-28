package satya.app.healthcareapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import satya.app.healthcareapp.listeners.OnItemClickListener
import satya.app.healthcareapp.databinding.ItemDashboardBinding
import satya.app.healthcareapp.models.DashboardModel

class DashboardAdapter(
    private val context: Context,
    private val arrayList: ArrayList<DashboardModel>,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<DashboardAdapter.ViewModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        val binding =
            ItemDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewModel(binding)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewModel, position: Int) {
        with(holder) {
            with(arrayList[position]) {
                binding.tvTitle.text = title
                binding.tvDescription.text = description
                binding.ivModuleImage.setImageDrawable(context.getDrawable(imgId))
                binding.root.setOnClickListener {
                    onItemClickListener.onClick(adapterPosition, it)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewModel(val binding: ItemDashboardBinding) : RecyclerView.ViewHolder(binding.root)
}