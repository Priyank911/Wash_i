import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wash_i.R

class ShopAdapter(private val shopList: List<ServiceProvider>) : RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.itemshop, parent, false)
        return ShopViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val shop = shopList[position]
        holder.tvShopName.text = shop.name
        holder.tvPincode.text = "Pincode: ${shop.pincode}"
        holder.tvServices.text = "Services: ${shop.services.joinToString(", ")}"
        holder.tvAddress.text = "Address: ${shop.shopAddress}"
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class ShopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvShopName: TextView = itemView.findViewById(R.id.tvShopName)
        val tvPincode: TextView = itemView.findViewById(R.id.tvPincode)
        val tvServices: TextView = itemView.findViewById(R.id.tvServices)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
    }
}
