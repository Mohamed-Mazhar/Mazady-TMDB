package com.example.mazadytmdb.features.moviedetails.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mazadytmdb.R
import com.example.mazadytmdb.databinding.MovieDetailsScreenBinding
import com.example.mazadytmdb.features.moviedetails.domain.model.MovieDetails
import com.example.mazadytmdb.features.moviedetails.presentation.viewmodel.MovieDetailsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsScreen : Fragment() {

    private var _binding: MovieDetailsScreenBinding? = null
    private val binding get() = _binding!!
    private val args: MovieDetailsScreenArgs by navArgs()
    private val viewModel: MovieDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        viewModel.loadMovieDetails(args.movieId)
    }

    private fun formatRuntime(minutes: Int): String {
        val hours = minutes / 60
        val mins = minutes % 60
        return if (hours > 0) "${hours}h ${mins}m" else "${mins}m"
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.movieDetails.collect { displayMovie(it) } }
                launch { viewModel.isLoading.collect { updateLoading(it) } }
            }
        }
    }

    private fun updateLoading(isLoading: Boolean) {
        binding.loadingProgressBar.visibility = if (isLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun displayMovie(movie: MovieDetails?) {
        movie?.let {
            // Load backdrop image
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280${it.backdropPath}")
                .into(binding.movieBackdrop)

            binding.movieTitle.text = it.title
            binding.movieOverview.text = it.overview

            val metadata = listOfNotNull(
                it.genres.joinToString(", ") { genre -> genre.name },
                it.releaseDate?.take(4),
                it.runtime?.let { runtime -> formatRuntime(runtime) }
            ).joinToString(" · ")
            binding.movieMetadata.text = metadata

            binding.movieGenres.text = "Genres: ${it.genres?.joinToString(", ") { genre -> genre.name } ?: "N/A"}"
            binding.movieRuntime.text = "Runtime: ${formatRuntime(it.runtime ?: 0)}"
            binding.movieReleaseDate.text = "Release Date: ${it.releaseDate ?: "N/A"}"
            binding.movieRating.text = "Rating: ${it.voteAverage}/10"
            binding.favoriteButton.setOnClickListener { _ ->
                viewModel.toggleFavorite(it)
            }
            if (it.isFavorite) {
                binding.favoriteButton.setImageResource(R.drawable.favorite_icon_filled)
            } else {
                binding.favoriteButton.setImageResource(R.drawable.favorite_icon_outlined)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}