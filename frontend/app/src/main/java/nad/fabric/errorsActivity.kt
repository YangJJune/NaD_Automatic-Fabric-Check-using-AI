package nad.fabric

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import nad.fabric.databinding.ActivityErrorsBinding
import nad.fabric.databinding.ActivityInfoBinding

class errorsActivity: AppCompatActivity() {
    lateinit var binding: ActivityErrorsBinding
    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorsBinding.inflate(layoutInflater)
        initLayout()
        setContentView(binding.root)

        binding.button3.setOnClickListener {
            val tmpIntent = Intent(this, moreErrorsActivity::class.java)
            launcher.launch(tmpIntent)
        }
        binding.button2.setOnClickListener {
            finish()
        }
    }
    fun initLayout(){

    }
}