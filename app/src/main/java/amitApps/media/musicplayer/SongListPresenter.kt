package amitApps.media.musicplayer

import android.util.SparseArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongListPresenter constructor(view: SongListView): BasePresenter<SongListView>(view) {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + Job())

    private lateinit var player: PlayerService
    private val filteredSongList: SparseArray<Song> = SparseArray()
    private lateinit var adapter: SongListAdapter

    fun setPlayerManager(playerService: PlayerService) {
        player = playerService
        loadSongList()
    }

    private fun loadSongList() {
        scope.launch {
            view.showLoading()

            player.readSong()
        }
    }


    fun onSongClick(index: Int) {
        view.onSongClick()

        val position = filteredSongList.keyAt(index)
        playSong(position)
    }
    fun fetchSongState() {
        player.getSong()?.let {
            view.updateSongState(it, player.isPlaying())
        }
    }

    private fun playSong(position: Int) {
        player.play(position)
    }

    fun filterSong(key: String) {
        scope.launch {
            filteredSongList.clear()

            val list = mutableListOf<Song>()
            player.getSongList().forEachIndexed { index, song ->
                if (song.name.contains(key, true) || song.author.contains(key, true)) {
                    filteredSongList.put(index, song)
                    list.add(song)
                }
            }

            withContext(Dispatchers.Main) {
                adapter.submitList(list)
            }
        }
    }
    fun onSongPlay() {
        if (!player.isPlaying()) {
            player.play()
        } else {
            player.pause()
        }
    }


}
