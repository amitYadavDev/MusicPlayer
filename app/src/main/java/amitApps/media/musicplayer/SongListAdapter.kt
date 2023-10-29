package amitApps.media.musicplayer

import amitApps.media.musicplayer.databinding.ActivitySongListBinding
import amitApps.media.musicplayer.databinding.AdapterSongListBinding
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class SongListAdapter(private val presenter: SongListPresenter):
    ListAdapter<Song, SongListAdapter.SongHolder>(SongItemCallback()) {

        inner class SongHolder(val viewBinding: AdapterSongListBinding):
        RecyclerView.ViewHolder(viewBinding.root) {
            init {
                itemView.setOnClickListener{
                    presenter.onSongClick(adapterPosition)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        val viewBinding =
            AdapterSongListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongHolder(viewBinding)
    }

}
