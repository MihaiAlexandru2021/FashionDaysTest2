package mihai.alex.fashiondaystest

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import mihai.alex.fashiondaystest.data.Element
import mihai.alex.fashiondaystest.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private lateinit var viewModel: FashionDaysViewModel
    private val binding get() = _binding!!
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var fashionRecycler: RecyclerView
    lateinit var fashionAdapter: FashionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[FashionDaysViewModel::class.java]
        swipeRefreshLayout = binding.container
        fashionRecycler = binding.rvFashionDays

        return binding.root

    }

    private fun setupRecycler(product: List<Element>) {
        fashionAdapter = FashionAdapter(product)
        fashionRecycler.adapter = fashionAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabUp.setOnClickListener {
            fashionRecycler.layoutManager?.smoothScrollToPosition(fashionRecycler, null, 0)
        }
        lifecycleScope.launch {
            viewModel.clothingState.collect{
                if (it.isEmpty()) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                    setupRecycler(it)
                }
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refreshList()
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deleteProduct: Element =
                    viewModel.clothingState.value[viewHolder.adapterPosition]
                val position = viewHolder.adapterPosition

                viewModel.removeProduct(position)
                fashionAdapter.notifyDataSetChanged()

                Snackbar.make(
                    fashionRecycler,
                    "Deleted " + deleteProduct.product?.productName,
                    Snackbar.LENGTH_LONG
                ).setAction("Undo") {
                    viewModel.undoProduct(position, deleteProduct)
                    fashionAdapter.notifyDataSetChanged()
                }.show()
            }
        }).attachToRecyclerView(fashionRecycler)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}