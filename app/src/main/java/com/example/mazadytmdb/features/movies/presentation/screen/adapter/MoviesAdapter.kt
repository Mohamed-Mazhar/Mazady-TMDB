import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mazadytmdb.R
import com.example.mazadytmdb.databinding.MovieItemBinding
import com.example.mazadytmdb.features.movies.data.dto.MovieDto
import com.example.mazadytmdb.features.movies.domain.model.Movie

class MoviesAdapter(
    private val onItemClick: (Movie) -> Unit,
    private val onFavoriteClicked: (Movie) -> Unit
) : ListAdapter<Movie, MoviesAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // DiffUtil callback for efficient updates
    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    inner class MovieViewHolder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                // Load poster image with Glide
                Glide.with(root.context)
                    .load(movie.posterUrl)
                    .centerCrop()
                    .into(moviePoster)

                movieTitle.text = movie.title
                movieReleaseDate.text = movie.releaseDate
                root.setOnClickListener { onItemClick(movie) }
                favoriteButton.setOnClickListener { onFavoriteClicked(movie) }
                if (movie.isFavorite) {
                    favoriteButton.setImageResource(R.drawable.favorite_icon_filled)
                } else {
                    favoriteButton.setImageResource(R.drawable.favorite_icon_outlined)
                }
            }
        }
    }
}