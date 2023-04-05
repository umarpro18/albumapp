package com.example.album.ui.albums

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.album.R
import com.example.album.data.entities.Album
import com.example.album.databinding.FragmentAlbumListBinding
import com.example.album.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumListFragment : Fragment() {

    private var binding: FragmentAlbumListBinding by autoCleared()
    private val viewModel: AlbumListViewModel by viewModels()
    private lateinit var adapter: AlbumListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchMenu()
        setUpAlbumList()
        setUpObserver()
    }

    private fun setUpAlbumList() {
        adapter = AlbumListAdapter { album: Album -> onAlbumClicked(album) }
        binding.recyclerViewAlbums.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewAlbums.adapter = adapter
    }

    private fun setUpObserver() {
        viewModel.albumListViewState.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it is AlbumListViewModel.ViewState.Loading
            when (it) {
                is AlbumListViewModel.ViewState.AlbumListLoaded -> {
                    adapter.setItems(it.albums.albumList as ArrayList<Album>)
                }
                is AlbumListViewModel.ViewState.AlbumListLoadFailure -> {
                    Toast.makeText(
                        this.requireContext(), it.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()

                }
                else -> {}
            }
        }
    }

    private fun onAlbumClicked(album: Album) {
        //Todo
    }

    private fun setupSearchMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                val search = menu.findItem(R.id.action_search)
                val searchView = search.actionView as SearchView?

                searchView?.imeOptions = EditorInfo.IME_ACTION_DONE

                searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(text: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(text: String?): Boolean {
                        adapter.filter.filter(text)
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}