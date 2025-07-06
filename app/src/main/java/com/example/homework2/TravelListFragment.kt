package com.example.homework2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TravelListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var placeAdapter: PlaceAdapter
    private val PREF_NAME = "travel_data"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_travel_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        loadPlaces()

        return view
    }

    private fun loadPlaces() {
        val prefs = activity?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs?.getString("places", null)
        val type = object : TypeToken<List<Place>>() {}.type
        val places: List<Place> = if (json != null) Gson().fromJson(json, type) else emptyList()

        placeAdapter = PlaceAdapter(places)
        recyclerView.adapter = placeAdapter
    }
}
