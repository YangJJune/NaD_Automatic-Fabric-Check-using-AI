package nad.fabric

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import nad.fabric.databinding.ActivityChartBinding
import org.json.JSONArray
import org.jsoup.Jsoup

class chartActivity : AppCompatActivity() {

    // on below line we are creating a
    // variable for our scatter chart .
    lateinit var scatteredChart: ScatterChart
    lateinit var binding: ActivityChartBinding
    val defectArr = arrayListOf<defectData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChartBinding.inflate(layoutInflater)
        binding.button2.setOnClickListener {
            finish()
        }
        setContentView(binding.root)

        // on below line we are initializing our all variables.
        scatteredChart = binding.idChart

        // on below line we are disabling the
        // description of our scattered chart
        scatteredChart.description.isEnabled = false

        // on below line we are setting
        // draw grid background as false
        scatteredChart.setDrawGridBackground(false)

        // on below line we are setting
        // touch enabled for our chart
        scatteredChart.setTouchEnabled(true)

        // on below line we are setting
        // max highlight distance
        scatteredChart.maxHighlightDistance = 50f

        // on below line we are setting
        // drag enabled for our chart
        scatteredChart.isDragEnabled = true

        // on below line we are enabling
        // scale for our chart
        scatteredChart.setScaleEnabled(true)

        // on below line we are setting
        // max visibility value count
        scatteredChart.setMaxVisibleValueCount(200)

        // on below line we are setting
        // pinch zoom for our chart
        scatteredChart.setPinchZoom(true)
        scatteredChart.setBackgroundColor(Color.WHITE)

        // on below line we are creating a
        // variable to get out legend for our chart
        val legend = scatteredChart.legend

        // on below line we are setting vertical, horizontal
        // and orientation for our legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL

        // on below line we are setting
        // draw inside for our legend
        legend.setDrawInside(true)

        // on below line we are setting
        // offset for our legend
        legend.xOffset = 5f

        // on below line we are getting
        // y axis for our chart
        val yl = scatteredChart.axisLeft

        // on below line we are
        // setting axis minimum
        yl.axisMinimum = -100f
        yl.axisMaximum = 3000f
        // on below line we are enabling
        // our right axis for our chart
        scatteredChart.axisRight.isEnabled = false
        yl.setDrawGridLines(false)
        yl.setDrawLabels(false)
        // on below line we are getting x axis of our chart
        val xl = scatteredChart.xAxis
        xl.axisMinimum = -100f
        xl.axisMaximum = 3000f
        // on below line we are setting draw grid lines
        xl.setDrawGridLines(false)
        xl.setDrawLabels(false)

        // in below line we are creating an array list
        // for each entry of our chart.
        // we will be representing three values in our charts.
        // below is the line where we are creating three
        // lines for our chart.
        val values1: ArrayList<Entry> = ArrayList()
        val values2: ArrayList<Entry> = ArrayList()
        val values3: ArrayList<Entry> = ArrayList()
        val fabric = intent.getSerializableExtra("fabric") as fabricData
        val fabric_id = fabric.id
        binding.idView3.text = fabric.id
        binding.dateView3.text = fabric.date
        binding.defectView3.text = fabric.d_cnt.toString()
        binding.totalView3.text = String.format("%.2f",(fabric.total_cnt.toDouble()/(91.44).toDouble()))
        CoroutineScope(Dispatchers.IO).launch {
            CoroutineScope(Dispatchers.IO).async {
                val jsonArr = JSONArray(Jsoup.connect("http://49.173.62.69:3000/defect_fabric_parent/"+fabric_id).ignoreContentType(true).get().body().text())
                for (i: Int in 0 until jsonArr.length()) {
                    val j = jsonArr.getJSONObject(i)
                    val id = j.getString("defect_code")
                    val parent = j.getInt("parent_fabric")
                    val x = j.getDouble("x")
                    val y = j.getDouble("y")
                    val issueName = j.getString("issue_name")
                    if(issueName.lowercase().equals("hole")){
                        values1.add(Entry(x.toFloat(),y.toFloat()))
                    }else if(issueName.lowercase().equals("puckering")){
                        values2.add(Entry(x.toFloat(),y.toFloat()))
                    }else{
                        values3.add(Entry(x.toFloat(),y.toFloat()))
                    }
                    val imagePath = j.getString("image_path")
                    var date = j.getString("timestamp")
                    date = date.replace('T', ' ')
                    date = date.substring(0, 16)

                    Log.d("test",j.toString())
                }
            }.await()
            // on below line we are creating a new point
            // for our scattered chart
            val set1 = ScatterDataSet(values1, "hole")

            // on below line we are setting out shape to circle
            set1.setScatterShape(ScatterChart.ScatterShape.CIRCLE)

            // on below line we are setting color to our point in chart
            set1.scatterShapeHoleColor = Color.RED

            // on below line we are setting scatter shape holder radius
            set1.scatterShapeHoleRadius = 3f

            // on below line we are setting color for our set
            set1.setColor(Color.RED)

            // on below line we are setting
            // shape size for all sets

            set1.scatterShapeSize = 8f

            // on below line we are creating a new point
            // for our scattered chart
            val set2 = ScatterDataSet(values2, "puckering")

            // on below line we are setting out shape to circle
            set2.setScatterShape(ScatterChart.ScatterShape.CIRCLE)

            // on below line we are setting color to our point in chart
            set2.scatterShapeHoleColor = Color.BLUE

            // on below line we are setting scatter shape holder radius
            set2.scatterShapeHoleRadius = 3f

            // on below line we are setting color for our set
            set2.setColor(Color.BLUE)

            // on below line we are setting
            // shape size for all sets

            set2.scatterShapeSize = 8f

            // on below line we are creating a new point
            // for our scattered chart
            val set3 = ScatterDataSet(values2, "stain")

            // on below line we are setting out shape to circle
            set3.setScatterShape(ScatterChart.ScatterShape.CIRCLE)

            // on below line we are setting color to our point in chart
            set3.scatterShapeHoleColor = Color.GREEN

            // on below line we are setting scatter shape holder radius
            set3.scatterShapeHoleRadius = 3f

            // on below line we are setting color for our set
            set3.setColor(Color.GREEN)

            // on below line we are setting
            // shape size for all sets
            set3.scatterShapeSize = 8f
            // on below line we are creating a new list for our data set
            val dataSet: ArrayList<IScatterDataSet> = ArrayList()

            // on below line we are adding
            // all sets to our data sets
            dataSet.add(set1)
            dataSet.add(set2)
            dataSet.add(set3)
            // on below line we are creating a
            // new object for scattered data
            val data: ScatterData = ScatterData(dataSet)

            // on below line we are setting
            // data to our chart
            scatteredChart.data = data
            scatteredChart.data.setDrawValues(false)

            // on below line we are calling
            // invalidate to display our chart
            scatteredChart.invalidate()
        }

    }
}