package amitApps.media.musicplayer

interface PlaySongView : BaseView {
        fun updateSongState(song: Song, isPlaying: Boolean, progress: Int)

        fun showRepeat(isRepeat: Boolean)

        fun showRandom(isRandom: Boolean)
}
