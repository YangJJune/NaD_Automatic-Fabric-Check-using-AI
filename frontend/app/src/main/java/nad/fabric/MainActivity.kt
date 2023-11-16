package nad.fabric

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import nad.fabric.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val pw = listOf("1234")
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            if(pw.contains(binding.editTextTextPassword.text.toString())){
                //move to infoActivity
                val tmpIntent = Intent(this, InfoActivity::class.java)
                launcher.launch(tmpIntent)
            }
        }

        initImageView()
    }
    fun initImageView(){
        //binding.imageView.setColorFilter(Color.parseColor("#ffff0000"), PorterDuff.Mode.SRC_IN);
    }
}