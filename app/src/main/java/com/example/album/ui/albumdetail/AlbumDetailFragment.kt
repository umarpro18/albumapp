package com.example.album.ui.albumdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.album.R
import com.example.album.data.entities.Album
import com.example.album.databinding.FragmentAlbumDetailBinding
import com.example.album.ui.albums.ARG_ALBUM
import com.example.album.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple album detail subclass.
 */
@AndroidEntryPoint
class AlbumDetailFragment : Fragment() {
    private var binding: FragmentAlbumDetailBinding by autoCleared()
    private val viewModel: AlbumDetailViewModel by viewModels()
    private lateinit var adapter: SimilarCategoryAlbumListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Album>(ARG_ALBUM)?.let {

            viewModel.getSimilarAlbumsBasedOnCategory(it.title, it.category)

            binding.apply {
                textViewArtistName.text = it.artist
                textViewCategoryLabel.text = it.category
                textViewItemCountLabel.text = it.itemCount
                textViewPriceLabel.text = it.price

                Glide.with(binding.root)
                    .load(it.imageUrl60)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imageViewAlbum)
            }
        }

        setUpSimilarCategoryAlbumList()
        setupObserver()
    }

    private fun setUpSimilarCategoryAlbumList() {
        adapter = SimilarCategoryAlbumListAdapter { album: Album -> onAlbumClicked(album) }
        binding.recyclerViewAlbumsCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewAlbumsCategory.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.similarAlbumsBasedOnCategory.observe(viewLifecycleOwner) {
            if(it.albumList.isNotEmpty()) {
                adapter.setItems(it.albumList as ArrayList<Album>)
            } else {
                binding.textViewSimilarCategoryListLabel.isVisible = false
            }
        }
    }

    private fun onAlbumClicked(album: Album) {
        val bundle = Bundle()
        bundle.putParcelable(ARG_ALBUM, album)
        findNavController().navigate(
            R.id.action_albumDetailFragment_to_AlbumDetailFragment, bundle
        )
    }
}