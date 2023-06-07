package mihai.alex.fashiondaystest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mihai.alex.fashiondaystest.data.Product

class FashionAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<FashionAdapter.FashionViewHolder>() {

    class FashionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewProduct: ImageView = itemView.findViewById(R.id.img_product)
        val textViewBrand: TextView = itemView.findViewById(R.id.tv_product_brand)
        val textViewName: TextView = itemView.findViewById(R.id.tv_product_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FashionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fashion_days_item, parent, false)

        return FashionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList?.size ?: 0
    }

    override fun onBindViewHolder(holder: FashionViewHolder, position: Int) {
        val product = productList.get(position)

        holder.textViewName.text = product.productName
        holder.textViewBrand.text = product.productBrand

        Picasso.with(holder.imageViewProduct.context)
            .load(product.productImages.thumb[0])
            .into(holder.imageViewProduct)


    }
}