package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityAddstationBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class AddStationActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private lateinit var binding: ActivityAddstationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addstation)
        binding = ActivityAddstationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            UserPreferencesRepository.getInstance(context = this@AddStationActivity).userPreferencesFlowCode.collect { value ->
                Log.v("CODE", value)
                binding.editTextStationCode.setText(value)
            }
        }
        lifecycleScope.launch {
            UserPreferencesRepository.getInstance(context = this@AddStationActivity).userPreferencesFlowName.collect { value ->
                Log.v("NAME", value)
                binding.editTextStationName.setText(value)
            }
        }
        lifecycleScope.launch {
            UserPreferencesRepository.getInstance(context = this@AddStationActivity).userPreferencesFlowLine.collect { value ->
                Log.v("LIST", value.toString())
                binding.spinner.setSelection(value)
            }
        }


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
            if (isInputValid()) {
                val code = binding.editTextStationCode.text.toString()
                val station = binding.editTextStationName.text.toString()
                val line = binding.spinner.selectedItem.toString()
                val mrt: MRT = MRT(0, code, station, line)
                launch(Dispatchers.IO) { model.addMRT(mrt) }
                lifecycleScope.launch {
                    UserPreferencesRepository.getInstance(context = this@AddStationActivity)
                        .update(
                            binding.editTextStationCode.text.toString(),
                            binding.editTextStationName.text.toString(),
                            binding.spinner.selectedItemPosition
                        )
                }
                finish()
            } else {
                Toast.makeText(this, "DK HOW TYPE IS IT", Toast.LENGTH_SHORT).show()

            }
        }

        binding.buttonClear.setOnClickListener {
            binding.editTextStationCode.setText("")
            binding.editTextStationName.setText("")
            binding.spinner.setSelection(0)
        }

    }

    private fun isInputValid(): Boolean {
        val numbers = "1234567890"
        if (binding.editTextStationCode.text.isEmpty()) {
            return false
        }
        if (binding.editTextStationName.text.isEmpty()) {
            return false
        }
        if (binding.editTextStationName.text.any { it in numbers }) {
            return false
        }
        return true
    }
}