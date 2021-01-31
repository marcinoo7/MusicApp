package pl.pwr.andz1.musicapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.pwr.andz1.musicapp.databinding.ListViewFragmentBinding


class ListFragment : Fragment() {

    lateinit var viewModel: ListViewModel
    private lateinit var binding : ListViewFragmentBinding
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager




    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = ListViewFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)
        viewAdapter = ListAdapter(viewModel, this.context, ::onClickSong)
        viewManager = LinearLayoutManager(this.context)
        binding.listFragment.apply{
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        return binding.root
    }

    private fun onClickSong(position : Int) {
        val action = ListFragmentDirections.actionListFragmentToSongFragment(position)
        NavHostFragment.findNavController(this).navigate(action)
    }

}