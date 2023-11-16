package nad.fabric

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import nad.fabric.databinding.ActivityInfoBinding

class InfoActivity: AppCompatActivity() {
    lateinit var binding: ActivityInfoBinding
    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        initLayout()
        binding.button3.setOnClickListener {
            val tmpIntent = Intent(this, errorsActivity::class.java)
            launcher.launch(tmpIntent)
        }
        binding.button2.setOnClickListener {
            finish()
        }
        setContentView(binding.root)
    }
    fun initLayout(){

    }
}