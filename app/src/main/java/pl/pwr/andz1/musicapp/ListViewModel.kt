package pl.pwr.andz1.musicapp

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import pl.pwr.andz1.musicapp.Data.Song

class ListViewModel : ViewModel() {

    private var songList: ArrayList<Song> = ArrayList()

    fun set_song_list(sList : ArrayList<Song>){
        songList = sList
    }

    fun get_song_list() : ArrayList<Song>
    {
        return songList
    }

    fun count_songs(): Int{
        return songList.count()
    }
}