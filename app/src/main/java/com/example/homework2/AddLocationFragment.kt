package com.example.homework2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddLocationFragment : Fragment() {
    private lateinit var inputName: EditText
    private lateinit var inputDescription: EditText
    private lateinit var button: Button
    private val PREF_NAME = "travel_data"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_location, container, false)
        inputName = view.findViewById(R.id.addLocationEditText)
        inputDescription = view.findViewById(R.id.addDescriptionEditText)
        button = view.findViewById(R.id.addButton)

        button.setOnClickListener {
            val name = inputName.text.toString().trim()
            val description = inputDescription.text.toString().trim()
            if (name.isNotEmpty() && description.isNotEmpty()) {
                savePlace(Place(name, description))
                inputName.text.clear()
                inputDescription.text.clear()
                Toast.makeText(context, "Place Added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Please enter both name and description", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun savePlace(place: Place) {
        val prefs = activity?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs?.getString("places", null)
        val type = object : TypeToken<MutableList<Place>>() {}.type
        val data: MutableList<Place> = if (json != null) Gson().fromJson(json, type) else mutableListOf()
        data.add(place)
        prefs?.edit()?.putString("places", Gson().toJson(data))?.apply()
    }
}
