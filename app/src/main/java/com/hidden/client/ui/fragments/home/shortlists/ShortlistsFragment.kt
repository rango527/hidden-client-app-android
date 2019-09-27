package com.hidden.client.ui.fragments.home.shortlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hidden.client.R

class ShortlistsFragment : Fragment() {

    private lateinit var shortlistsViewModel: ShortlistsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shortlistsViewModel =
            ViewModelProviders.of(this).get(ShortlistsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        shortlistsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}