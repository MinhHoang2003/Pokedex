package com.example.pokemonapp.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R
import com.example.pokemonapp.view.itemdetail.ItemDetailActivity

class ItemsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var homeViewModel: HomeViewModel
    val isLoading = MutableLiveData<Boolean>()
    var itemsAdapter: ItemsAdapter? = null
    var isLoadMore: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_home_fragmen, container, false)
        homeViewModel = activity?.run {
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        }!!
        recyclerView = view.findViewById(R.id.recyclerMain)
        observerData(context!!)
        initScrollListener()
        return view
    }

    private fun observerData(context: Context) {
        homeViewModel.getItems(1)
        homeViewModel.items.observe(this, Observer { it ->
            if (itemsAdapter == null) {
                itemsAdapter = ItemsAdapter(it, context)
                recyclerView.adapter = itemsAdapter
                recyclerView.layoutManager = LinearLayoutManager(context)
            } else {
                val data = itemsAdapter!!.data.items
                val scrollPosition = data.size - 1
                data.removeAt(data.size - 1)
                itemsAdapter!!.notifyItemRemoved(data.size)
                data.addAll(it.items)
                itemsAdapter!!.notifyItemRangeInserted(
                    scrollPosition,
                    itemsAdapter!!.data.items.size
                )
                Log.d(
                    "load_more",
                    "in adapter ${itemsAdapter!!.data.items.size} focus on $scrollPosition"
                )
                recyclerView.scrollToPosition(scrollPosition - 1)
                isLoadMore = false
            }
            itemsAdapter?.onItemsClick = {
                val intent = Intent(getContext(), ItemDetailActivity::class.java)
                intent.putExtra("item_name", it?.name)
                startActivity(intent)
            }
        })
        homeViewModel.query.observe(this,
            Observer<String> { t ->
                if (isLoading.value == false) {
                    itemsAdapter?.filter?.filter(t)
                }
            })
    }

    private fun initScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager: LinearLayoutManager =
                    recyclerView.layoutManager as LinearLayoutManager
                Log.d("load_more", "in scroll listener")
                val dataSize: Int? = itemsAdapter?.data?.items?.size
                val total: Int? = itemsAdapter?.data?.total
                if (dataSize != null && total != null &&
                    linearLayoutManager.findLastCompletelyVisibleItemPosition() == dataSize - 1 &&
                    dataSize < total
                ) {
                    Log.d("load_more", "in scroll listener $dataSize $total")
                    if (!isLoadMore) {
                        loadMore()
                        isLoadMore = true
                    }
                }
            }
        })
    }

    private fun loadMore() {
        Log.d("load_more", "in load more fun")
        itemsAdapter!!.data.items.add(null)
        itemsAdapter!!.notifyItemInserted(itemsAdapter!!.data.items.size - 1)
        Handler().postDelayed({
            homeViewModel.getItems(itemsAdapter!!.currentPage++)
        }, 1000)
    }

}