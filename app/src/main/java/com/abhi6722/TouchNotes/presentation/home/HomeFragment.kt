package com.abhi6722.TouchNotes.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.abhi6722.TouchNotes.R
import com.abhi6722.TouchNotes.database.NotesDataBase
import com.abhi6722.TouchNotes.databinding.FragmentHomeBinding
import com.abhi6722.TouchNotes.entities.Notes
import com.abhi6722.TouchNotes.presentation.create_notes.CreateNoteFragment
import com.abhi6722.TouchNotes.util.viewBinding
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    var arrNotes = ArrayList<Notes>()
    var notesAdapter: NotesAdapter = NotesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            context?.let {

                val notes = NotesDataBase.getDataBase(it).noteDao().getAllNotes()
                notesAdapter.setData(notes)
                arrNotes = notes as ArrayList<Notes>
                binding.recyclerView.adapter = notesAdapter
            }
        }

        notesAdapter.setOnClickListener(onClicked)

        // FAB CREATE NOTE FRAGMENT
        binding.fabCreateNoteBtn.setOnClickListener {
            replaceFragment(CreateNoteFragment.newInstance(), true)
        }

        binding.searchView.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {

                    val tempArr = ArrayList<Notes>()

                    for (arr in arrNotes) {
                        if (arr.title!!.toLowerCase(Locale.getDefault()).contains(p0.toString())) {
                            tempArr.add(arr)
                        }
                    }
                    notesAdapter.setData(tempArr)
                    notesAdapter.notifyDataSetChanged()
                    return true
                }
            })
    }

    private val onClicked = object : NotesAdapter.OnItemClickListener {
        override fun onClicked(notesId: Int) {

            val fragment: Fragment
            val bundle = Bundle()
            bundle.putInt("noteId", notesId)
            fragment = CreateNoteFragment.newInstance()
            fragment.arguments = bundle

            replaceFragment(fragment, true)
        }
    }

    fun replaceFragment(fragment: Fragment, istransition: Boolean) {

        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        if (istransition) {
            fragmentTransition.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }
        fragmentTransition.replace(R.id.flFragmenet, fragment)
            .addToBackStack(fragment.javaClass.simpleName)
        fragmentTransition.commit()
    }
}
