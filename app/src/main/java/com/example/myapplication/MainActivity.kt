package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listViewMRT.addItemDecoration(//DIVIDER
            DividerItemDecoration(
                binding.listViewMRT.context,
                DividerItemDecoration.VERTICAL
            )
        )

        binding.listViewMRT.layoutManager = LinearLayoutManager(this)

        val model: MRTViewModel by viewModels {
            MRTViewModelFactory((application as MRTApplication).repository)
        }

        model.MRTList.observe(this) {
            val data = model.MRTList.value
            val adapter = data?.let { MRTListAdapter(it) }
            binding.listViewMRT.adapter = adapter
        }


        binding.fab.setOnClickListener {
            val intent = Intent(this, AddStationActivity::class.java)
            startActivity(intent)
        }

    }
}