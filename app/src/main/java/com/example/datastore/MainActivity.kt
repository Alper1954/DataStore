package com.example.datastore


import android.os.Bundle
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.datastore.data.SettingsDataStore
import com.example.datastore.data.dataStore
import com.example.datastore.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var layoutDataStore: SettingsDataStore

    private var option1Checked: Boolean = false
    private var option2Checked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layoutDataStore = SettingsDataStore(this.dataStore)

        layoutDataStore.option1Flow.asLiveData().observe(this, { value ->
            option1Checked = value
            binding.option1.isChecked = option1Checked
        })

        layoutDataStore.option2Flow.asLiveData().observe(this, { value ->
            option2Checked = value
            binding.option2.isChecked = option2Checked
        })

        binding.option1.setOnClickListener {
            option1Checked = (it as CheckBox).isChecked
            lifecycleScope.launch {
                layoutDataStore.saveOption1ToPreferencesStore(
                    option1Checked,
                    this@MainActivity
                )
            }
        }

        binding.option2.setOnClickListener {
            option2Checked = (it as CheckBox).isChecked
            lifecycleScope.launch {
                layoutDataStore.saveOption2ToPreferencesStore(
                    option2Checked,
                    this@MainActivity
                )
            }
        }
    }
}