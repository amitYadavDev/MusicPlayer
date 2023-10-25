package amitApps.media.musicplayer

import android.content.Context
import android.view.View

interface SongListView: BaseView {
        fun showLoading()

        fun stopLoading()

        fun updateSongState(song: Song, isPlaying: Boolean)

        fun onSongClick()
}
