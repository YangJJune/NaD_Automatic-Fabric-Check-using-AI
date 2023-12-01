package nad.fabric

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
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

        binding.defectView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val defectArr = arrayListOf<defectData>()
        var fabric_id:String = ""
        if(intent.hasExtra("fabric_id")){
            fabric_id = intent.getStringExtra("fabric_id").toString()
        }

        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        binding.defectView.addItemDecoration(decoration)
        CoroutineScope(Dispatchers.IO).launch {
            CoroutineScope(Dispatchers.IO).async {
                val jsonArr = JSONArray(

                    Jsoup.connect("http://49.173.62.69:3000/defect_fabric_parent/"+fabric_id).ignoreContentType(true).get()

                        .body().text()
                )
                for (i: Int in 0 until jsonArr.length()) {
                    val j = jsonArr.getJSONObject(i)
                    Log.d("defect", j.toString())
                    val id = j.getString("defect_code")
                    val parent = j.getInt("parent_fabric")
                    val x = j.getDouble("x")
                    val y = j.getDouble("y")
                    val issueName = j.getString("issue_name")
                    val imagePath = j.getString("image_path")
                    var date = j.getString("timestamp")
                    date = date.replace('T', ' ')
                    date = date.substring(0, 16)
                    defectArr.add(defectData(id, parent.toString(), date, issueName,x.toFloat(),y.toFloat(), imagePath))
                }
                Log.d("test", defectArr.toString())
            }.await()
            val defectAdapter = defectAdapter(defectArr)
            this@moreErrorsActivity.runOnUiThread{
                binding.defectView.adapter = defectAdapter
            }
        }
        setContentView(binding.root)
    }
    fun initLayout(){

    }
}