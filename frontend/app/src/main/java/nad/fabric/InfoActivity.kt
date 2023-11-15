package nad.fabric

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nad.fabric.databinding.ActivityInfoBinding

class InfoActivity: AppCompatActivity() {
    lateinit var binding: ActivityInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        initLayout()
        setContentView(binding.root)

    }
    fun initLayout(){

    }
}