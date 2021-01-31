package pl.pwr.andz1.musicapp.Data

class Song(
    private val title : String,
    private val artist : String,
    private val album : String,
    private val duration : String,
    private val path : String
)
{

    fun getPath() : String {
        return path
    }
    fun getTitle() : String {
        return title
    }
    fun getArtist() : String {
        return artist
    }
    fun getAlbum() : String {
        return album
    }
    fun getDuration() : String {
        return duration
    }

}