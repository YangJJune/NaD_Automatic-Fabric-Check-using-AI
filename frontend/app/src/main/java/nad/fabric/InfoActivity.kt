package nad.fabric

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
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
    val fabricArr = arrayListOf<fabricData>()
    val searchArr = arrayListOf<fabricData>()
    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        initLayout()
        binding.button3.setOnClickListener {
            //val tmpIntent = Intent(this, errorsActivity::class.java)
            //launcher.launch(tmpIntent)
            fabricArr.reverse()
            binding.infoView.adapter!!.notifyDataSetChanged()
        }
        binding.button2.setOnClickListener {
            finish()
        }
        binding.searchBar.addTextChangedListener {
            searchArr.clear()
            for(i in 0 until fabricArr.size){
                if(fabricArr.get(i).id.contains(binding.searchBar.text) ||
                    fabricArr.get(i).date.contains(binding.searchBar.text)){
                    searchArr.add(fabricArr.get(i))
                }
            }
            binding.infoView.adapter = fabricAdapter(searchArr)
            binding.infoView.adapter!!.notifyDataSetChanged()
        }

        binding.infoView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        CoroutineScope(Dispatchers.IO).launch {
            CoroutineScope(Dispatchers.IO).async {
                val jsonArr = JSONArray(Jsoup.connect("http://49.173.62.69:3000/fabrics").ignoreContentType(true).get().body().text())
                fabricArr.sortBy {
                    it.date
                }
                for(i:Int in 0 until jsonArr.length()) {
                    val j = jsonArr.getJSONObject(i)
                    val fCode = j.getString("fabric_id")
                    val total = j.getInt("total_count")
                    val defects = j.getInt("defect_count")
                    var date = j.getString("scan_start_time")
                    var complete_date:String? = j.getString("complete_time")
                    Log.d("complete_time",complete_date.toString())
                    val image_path:String? = j.getString("image_path")
                    date = date.replace('T',' ')
                    date = date.substring(0,16)
                    if (complete_date != null) {
                        complete_date = complete_date.replace('T',' ')
                        complete_date = complete_date.substring(0,16)
                    }
                    fabricArr.add(fabricData(fCode, date, defects, total, complete_date, image_path))
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
                infoAdapter.imageClickListener = object: fabricAdapter.OnLongClickListener{
                    override fun onItemLongClick(fData: fabricData) {
                        val tmpIntent = Intent(this@InfoActivity, imageActivity::class.java)
                        tmpIntent.putExtra("image_path", fData.image_path)
                        launcher.launch(tmpIntent)
                    }
                }
                infoAdapter.chartClickListener = object: fabricAdapter.OnChartClickListener{
                    override fun onChartClick(fData: fabricData) {
                        val tmpIntent = Intent(this@InfoActivity, chartActivity::class.java)
                        tmpIntent.putExtra("id", fData.id)
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