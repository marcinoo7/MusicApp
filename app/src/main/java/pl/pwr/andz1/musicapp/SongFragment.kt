package pl.pwr.andz1.musicapp

import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import pl.pwr.andz1.musicapp.Data.Song
import pl.pwr.andz1.musicapp.databinding.SongFragmentBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SongFragment : Fragment() {

    private lateinit var binding : SongFragmentBinding
    private lateinit var viewModel: ListViewModel
    private lateinit var song : Song
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SongFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)

        val position = arguments?.getInt("position")!!.toInt()
        song = viewModel.get_song_list()[position]

        binding.apply {
            Title.text =  song.getTitle()
            Artist.text = song.getArtist()
            val picture = getAlbumPicture(song.getPath())
            if(picture != null)
            {
                val bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.size)
                imageView.setImageBitmap(bitmap)
            }
            else imageView.setImageResource(R.drawable.no_image)

            playBtn.setOnClickListener { start_pause() }
            NextBtn.setOnClickListener {changeSong(Math.floorMod(position + 1, viewModel.count_songs()))}
            PreviousBtn.setOnClickListener { changeSong(Math.floorMod(position - 1, viewModel.count_songs())) }
            ResetBtn.setOnClickListener { reset() }
            BackBtn.setOnClickListener { toList() }
        }



        initializeSong()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun getAlbumPicture(path: String) : ByteArray?{
        var retriever = MediaMetadataRetriever()
        retriever.setDataSource(path)
        val picture = retriever.embeddedPicture
        retriever.release()
        return picture
    }

    private fun initializeSong() {
        mediaPlayer = MediaPlayer.create(this.context, Uri.parse(song.getPath()))
        start_pause()
    }

    private fun start_pause(){

        binding.apply {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                playBtn.text = context?.getString(R.string.play)
            }
            else {
                mediaPlayer.start()
                playBtn.text = context?.getString(R.string.stop)
            }
        }
    }

    private fun reset()
    {
        mediaPlayer.reset()
        initializeSong()
    }

    private fun changeSong(position : Int){
        mediaPlayer.release()
        val action = SongFragmentDirections.actionSongFragmentSelf2(position)
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun toList(){
        mediaPlayer.release()
        val action = SongFragmentDirections.actionSongFragmentToListFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

}