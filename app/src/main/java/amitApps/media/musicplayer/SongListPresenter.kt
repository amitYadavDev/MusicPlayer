package amitApps.media.musicplayer

import android.util.SparseArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class SongListPresenter constructor(view: SongListView): BasePresenter<SongListView>(view) {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + Job())

    private lateinit var player: PlayerService
    private val filteredSongList: SparseArray<Song> = SparseArray()



    fun onSongClick(index: Int) {
        view.onSongClick()

        val position = filteredSongList.keyAt(index)
        playSong(position)
    }

    private fun playSong(position: Int) {
        player.play(position)
    }

}
