package nad.fabric

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nad.fabric.databinding.ActivityErrorsBinding
import nad.fabric.databinding.ActivityInfoBinding

class errorsActivity: AppCompatActivity() {
    lateinit var binding: ActivityErrorsBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityErrorsBinding.inflate(layoutInflater)
        initLayout()
        setContentView(binding.root)

    }
    fun initLayout(){

    }
}