package com.example.mazadytmdb.features.movies.presentation.screen

import MoviesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mazadytmdb.R
import com.example.mazadytmdb.core.util.GridSpacingItemDecoration
import com.example.mazadytmdb.core.util.LinearSpacingItemDecoration
import com.example.mazadytmdb.databinding.MoviesScreenBinding
import com.example.mazadytmdb.features.movies.presentation.viewmodel.MoviesViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoviesScreen : Fragment() {

    private val viewModel: MoviesViewModel by viewModel()
    private var _binding: MoviesScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MoviesAdapter
    private var isGridView = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MoviesScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupRecyclerView()
        setupViewToggle()
        setupScrollListener()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                collectFlows()
            }
        }
    }

    private suspend fun collectFlows() = coroutineScope {
        launch { viewModel.movies.collect { adapter.submitList(it) } }
        launch { viewModel.isLoading.collect { updateLoading(it) } }
        launch { viewModel.error.collect { it?.let(::showError) } }
    }

    private fun setupRecyclerView() {
        adapter = MoviesAdapter(
            onItemClick = { movie ->
                val action = MoviesScreenDirections.actionMoviesListFragmentToMovieDetailsFragment(movie.id)
                findNavController().navigate(action)
            },
            onFavoriteClicked = { movie ->
                viewModel.toggleFavorite(movie)
            }
        )
        binding.moviesRecyclerView.apply {
            adapter = this@MoviesScreen.adapter
            layoutManager = createLayoutManager()
            addItemDecoration(createItemDecoration())
            setHasFixedSize(true)
        }
    }

    private fun createLayoutManager(): RecyclerView.LayoutManager {
        return if (isGridView) {
            GridLayoutManager(requireContext(), 2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int = 1
                }
            }
        } else {
            LinearLayoutManager(requireContext())
        }
    }


    private fun setupViewToggle() {
        binding.viewToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                isGridView = checkedId == R.id.gridViewButton
                toggleLayoutManager()
            }
        }
    }

    private fun toggleLayoutManager() {
        val spanCount = if (isGridView) 2 else 1
        val layoutManager = GridLayoutManager(requireContext(), spanCount)
        binding.moviesRecyclerView.layoutManager = layoutManager
    }


    private fun createItemDecoration(): RecyclerView.ItemDecoration {
        val spacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
        return if (isGridView) {
            GridSpacingItemDecoration(2, spacing, true)
        } else {
            LinearSpacingItemDecoration(spacing)
        }
    }

    private fun setupScrollListener() {
        binding.moviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (!viewModel.isLoading.value && lastVisibleItem >= totalItemCount - 5) {
                    viewModel.loadMoreMovies()
                }
            }
        })
    }

    private fun showError(errorMessage: String) {
        binding.errorTextView.apply {
            text = errorMessage
            visibility = View.VISIBLE
        }
    }

    private fun updateLoading(isLoading: Boolean) {
        if (isLoading) {
            if (adapter.itemCount == 0) {
                binding.moviesRecyclerView.visibility = View.GONE
                binding.errorTextView.visibility = View.GONE
            } else {
                binding.loadingProgressBar.visibility = View.VISIBLE
            }
        } else {
            binding.loadingProgressBar.visibility = View.GONE
            binding.moviesRecyclerView.visibility = View.VISIBLE
        }
    }
}