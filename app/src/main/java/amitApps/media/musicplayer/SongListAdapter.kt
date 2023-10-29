package amitApps.media.musicplayer

import amitApps.media.musicplayer.databinding.ActivitySongListBinding
import amitApps.media.musicplayer.databinding.AdapterSongListBinding
import android.os.Parcel
import android.os.Parcelable
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

}
