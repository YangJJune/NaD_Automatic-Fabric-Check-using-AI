package nad.fabric

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import nad.fabric.databinding.ActivityInfoBinding
import org.json.JSONArray
import org.jsoup.Jsoup

class InfoActivity: AppCompatActivity() {
    lateinit var binding: ActivityInfoBinding
    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        initLayout()
        binding.button3.setOnClickListener {
            //val tmpIntent = Intent(this, errorsActivity::class.java)
            //launcher.launch(tmpIntent)
        }
        binding.button2.setOnClickListener {
            finish()
        }

        binding.infoView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val fabricArr = arrayListOf<fabricData>()
        CoroutineScope(Dispatchers.IO).launch {
            CoroutineScope(Dispatchers.IO).async {
                val jsonArr = JSONArray(Jsoup.connect("http://49.173.62.69:3000/fabrics").ignoreContentType(true).get().body().text())
                for(i:Int in 0 until jsonArr.length()) {
                    val j = jsonArr.getJSONObject(i)
                    val fCode = j.getString("fabric_id")
                    val total = j.getInt("total_count")
                    val defects = j.getInt("defect_count")
                    var date = j.getString("scan_start_time")
                    date = date.replace('T',' ')
                    date = date.substring(0,16)
                    fabricArr.add(fabricData(fCode, date, defects, total))
                }
            }.await()

            val infoAdapter = fabricAdapter(fabricArr)

            this@InfoActivity.runOnUiThread {
                infoAdapter.itemClickListener = object: fabricAdapter.OnItemClickListener{
                    override fun OnItemClick(fData: fabricData) {
                        val tmpIntent = Intent(this@InfoActivity,moreErrorsActivity::class.java)
                        tmpIntent.putExtra("fabric_id", fData.id)
                        launcher.launch(tmpIntent)
                    }
                }
                binding.infoView.adapter = infoAdapter
            }
        }

        setContentView(binding.root)
    }
    fun initLayout(){

    }
}