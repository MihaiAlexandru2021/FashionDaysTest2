package mihai.alex.fashiondaystest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mihai.alex.fashiondaystest.data.Element
import mihai.alex.fashiondaystest.data.TypeItem

class FashionAdapter(private val productList: List<Element>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class FashionItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewProduct: ImageView = itemView.findViewById(R.id.img_product)
        val textViewBrand: TextView = itemView.findViewById(R.id.tv_product_brand)
        val textViewName: TextView = itemView.findViewById(R.id.tv_product_name)
    }

    class FashionHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val header: TextView = itemView.findViewById(R.id.header_brand)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            TypeItem.HEADER.value -> {
                val holder = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fashion_days_header, parent, false)
                return FashionHeaderViewHolder(holder)
            }
            TypeItem.PRODUCT.value -> {
                val holder = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fashion_days_item, parent, false)

                return FashionItemViewHolder(holder)
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = productList[position]
        if (item.type == TypeItem.PRODUCT) {
            holder as FashionItemViewHolder
            holder.textViewName.text = item.product?.productName
            holder.textViewBrand.text = item.product?.productBrand

            Picasso.with(holder.imageViewProduct.context)
                .load(item.product?.productImages?.thumb?.get(0) ?: "")
                .into(holder.imageViewProduct)

        } else {
            holder as FashionHeaderViewHolder
            holder.header.text = item.brand
        }
    }

    override fun getItemViewType(position: Int) = productList[position].type.value


    override fun getItemCount(): Int {
        return productList.size
    }
}