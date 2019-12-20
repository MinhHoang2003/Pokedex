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
import com.example.pokemonapp.view.movedetail.MoveDetailActivity

class MovesFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var homeViewModel: HomeViewModel
    val isLoading = MutableLiveData<Boolean>()
    var movesAdapter: MovesAdapter? = null
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
        homeViewModel.getMoves(1)
        homeViewModel.moves.observe(this, Observer { it ->
            if (movesAdapter == null || homeViewModel.isSearching) {
                movesAdapter = MovesAdapter(it, context)
                val linearLayoutManager = LinearLayoutManager(context)
                recyclerView.layoutManager = linearLayoutManager
                recyclerView.adapter = movesAdapter
                if (homeViewModel.canLoadMore) homeViewModel.isSearching = false
            } else {
                val data = movesAdapter!!.data.moves
                val scrollPosition = data.size - 1
                data.removeAt(data.size - 1)
                movesAdapter!!.notifyItemRemoved(data.size)
                data.addAll(it.moves)
                movesAdapter!!.notifyItemRangeInserted(
                    scrollPosition,
                    movesAdapter!!.data.moves.size
                )
                Log.d(
                    "load_more",
                    "in adapter ${movesAdapter!!.data.moves.size} focus on $scrollPosition"
                )
                recyclerView.scrollToPosition(scrollPosition - 1)
                isLoadMore = false
            }
            movesAdapter?.onItemsClick = {
                val intent = Intent(getContext(), MoveDetailActivity::class.java)
                intent.putExtra("move_name", it?.name)
                startActivity(intent)
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
                val dataSize: Int? = movesAdapter?.data?.moves?.size
                val total: Int? = movesAdapter?.data?.total
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
        movesAdapter!!.data.moves.add(null)
        movesAdapter!!.notifyItemInserted(movesAdapter!!.data.moves.size - 1)
        Handler().postDelayed({
            homeViewModel.getMoves(MovesAdapter.CURRENT_PAGE++)
        }, 1000)
    }

}