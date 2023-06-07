package mihai.alex.fashiondaystest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import mihai.alex.fashiondaystest.data.Product
import mihai.alex.fashiondaystest.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private lateinit var viewModel: FashionDaysViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var fashionRecycler: RecyclerView
    lateinit var fashionAdapter: FashionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[FashionDaysViewModel::class.java]

        swipeRefreshLayout = binding.container
        fashionRecycler = binding.rvFashionDays

        return binding.root

    }

    private fun setupRecycler(product: List<Product>) {
        fashionAdapter = FashionAdapter(product)
        fashionRecycler.adapter = fashionAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.productList.observe(viewLifecycleOwner, Observer {
            setupRecycler(it)
        })

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.getProducts()
            fashionAdapter.notifyDataSetChanged()
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deleteProduct: Product? =
                    viewModel.productList.value?.get(viewHolder.adapterPosition)
                val position = viewHolder.adapterPosition
                viewModel.productList.value?.removeAt(position)
                fashionAdapter.notifyItemRemoved(position)
                if (deleteProduct != null) {
                    Snackbar.make(
                        fashionRecycler,
                        "Deleted " + deleteProduct.productName,
                        Snackbar.LENGTH_LONG
                    ).setAction("Undo", View.OnClickListener {
                        viewModel.productList.value?.add(position, deleteProduct)
                        fashionAdapter.notifyItemInserted(position)
                    }).show()
                }
            }
        }).attachToRecyclerView(fashionRecycler)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}