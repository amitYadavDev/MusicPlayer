package amitApps.media.musicplayer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class SongListPresenter constructor(view: SongListView): BasePresenter<SongListView>(view) {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + Job())

    private lateinit var player: PlayerService

}
