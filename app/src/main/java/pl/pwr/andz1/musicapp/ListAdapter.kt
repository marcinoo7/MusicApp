package pl.pwr.andz1.musicapp

import android.content.Context
import android.database.Cursor
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.pwr.andz1.musicapp.Data.Song
import pl.pwr.andz1.musicapp.databinding.MusicDataBlocksBinding

class ListAdapter(private val myDataset: ListViewModel, context : Context?,
                  private val onClick : (Int) -> Unit):

    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val bind = MusicDataBlocksBinding.bind(view)
    }

    private var mContext : Context?

    init
    {
        mContext = context
        initializeList()
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ListAdapter.MyViewHolder {
        val binding =
            MusicDataBlocksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = myDataset.get_song_list()[position]

        holder.bind.apply {
            songTitleTextView.text =  data.getTitle()
            songArtistTextView.text = data.getArtist()
            songAlbumTextView.text = data.getAlbum()
            val picture = getAlbumPicture(data.getPath())
            if(picture != null)
            {
                val bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.size)
                albumImageView.setImageBitmap(bitmap)
            }
            else albumImageView.setImageResource(R.drawable.no_image)
        }
        holder.itemView.setOnClickListener { onClick(position) }
    }


    override fun getItemCount() = myDataset.get_song_list().size

    fun initializeList()
    {

        var tempList : ArrayList<Song> =  ArrayList();
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        var dataModel : Array<String> = arrayOf(
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST)

        var cursor : Cursor? = mContext?.contentResolver?.query(uri, dataModel,null, null, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val album : String = cursor.getString(0)
                val title : String = cursor.getString(1)
                val duration : String = cursor.getString(2)
                val path : String = cursor.getString(3)
                val artist : String = cursor.getString(4)

                val song = Song(title, artist, album, duration, path)
                tempList.add(song)
            }
            cursor.close();
        }
        myDataset.set_song_list(tempList)
    }

    private fun getAlbumPicture(path : String) : ByteArray?{
        var retriever = MediaMetadataRetriever()
        retriever.setDataSource(path)
        val picture = retriever.embeddedPicture
        retriever.release()
        return picture
    }
}