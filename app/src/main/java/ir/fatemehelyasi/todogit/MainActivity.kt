package ir.fatemehelyasi.todogit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import ir.fatemehelyasi.todogit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //for  change action bar

    }
}