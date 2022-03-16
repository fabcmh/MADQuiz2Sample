package com.example.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityAddstationBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddStationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddstationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addstation)
        binding = ActivityAddstationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ArrayAdapter.createFromResource(
            this,
            R.array.mrtLine_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinner.adapter = adapter
        }

        val model: MRTViewModel by viewModels {
            MRTViewModelFactory((application as MRTApplication).repository)
        }

        binding.buttonAdd.setOnClickListener {
            val mrt: MRT = MRT(0,"Help", "12", "12")
            GlobalScope.launch { model.addMRT(mrt) }
        }

    }
}