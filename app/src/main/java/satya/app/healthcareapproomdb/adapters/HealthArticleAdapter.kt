package satya.app.healthcareapproomdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import satya.app.healthcareapproomdb.databinding.ItemHealthArticlesBinding
import satya.app.healthcareapproomdb.db.entities.HealthArticleEntity
import satya.app.healthcareapproomdb.listeners.CommonItemListClickListener

class HealthArticleAdapter(
    val context: Context,
    private val healthArticleList: List<HealthArticleEntity>,
    private val commonItemListClickListener: CommonItemListClickListener<HealthArticleEntity>
) : RecyclerView.Adapter<HealthArticleAdapter.ViewHolder>() {


    class ViewHolder(val itemHealthArticlesBinding: ItemHealthArticlesBinding) :
        RecyclerView.ViewHolder(itemHealthArticlesBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHealthArticlesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return healthArticleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(healthArticleList[position]) {
                itemHealthArticlesBinding.tvArticleHeading.text = title
                itemHealthArticlesBinding.tvArticlesDate.text = uploadedOn
                itemHealthArticlesBinding.root.setOnClickListener {
                    commonItemListClickListener.onItemClick(healthArticleList[adapterPosition])
                }
            }
        }
    }
}
