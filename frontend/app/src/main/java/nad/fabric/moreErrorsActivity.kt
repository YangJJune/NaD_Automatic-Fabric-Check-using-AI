package nad.fabric

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import nad.fabric.databinding.ActivityMoreErrorsBinding
import org.json.JSONArray
import org.jsoup.Jsoup

class moreErrorsActivity: AppCompatActivity() {
    lateinit var binding: ActivityMoreErrorsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoreErrorsBinding.inflate(layoutInflater)
        initLayout()

        binding.button2.setOnClickListener {
            finish()
        }
        val parent_id = this.intent.getStringExtra("fabric_id")
        binding.defectView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val fabricArr = arrayListOf<fabricData>()
        CoroutineScope(Dispatchers.IO).launch {
            CoroutineScope(Dispatchers.IO).async {
                val jsonArr = JSONArray(
                    Jsoup.connect("http://49.173.62.69:3000/get-defects/"+parent_id).ignoreContentType(true).get()
                        .body().text()
                )
                for (i: Int in 0 until jsonArr.length()) {
                    val j = jsonArr.getJSONObject(i)
                    Log.d("test",j.toString())
//                    val fCode = j.getString("fabric_id")
//                    val total = j.getInt("total_count")
//                    val defects = j.getInt("defect_count")
//                    var date = j.getString("scan_start_time")
//                    date = date.replace('T', ' ')
//                    date = date.substring(0, 16)
//                    fabricArr.add(fabricData(fCode, date, defects, total,null,"1"))
                }
            }.await()
        }
        setContentView(binding.root)

    }
    fun initLayout(){

    }
}