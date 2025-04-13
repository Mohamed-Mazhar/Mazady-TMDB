import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.mazadytmdb.R
import com.example.mazadytmdb.databinding.MovieItemBinding
import com.example.mazadytmdb.features.movies.domain.model.Movie

class MoviesAdapter(
    private val onItemClick: (Movie) -> Unit,
    private val onFavoriteClicked: (Movie) -> Unit,
    private val onImageLoaded: (Movie, Bitmap) -> Unit
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
                    .asBitmap()
                    .load(movie.posterUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            moviePoster.setImageBitmap(resource)
                            onImageLoaded(movie, resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })

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