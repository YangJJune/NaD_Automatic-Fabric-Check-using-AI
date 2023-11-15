package nad.fabric

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nad.fabric.databinding.ActivityInfoBinding
import nad.fabric.databinding.ActivityMoreErrorsBinding

class moreErrorsActivity: AppCompatActivity() {
    lateinit var binding: ActivityMoreErrorsBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMoreErrorsBinding.inflate(layoutInflater)
        initLayout()
        setContentView(binding.root)

    }
    fun initLayout(){

    }
}