package nad.fabric

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nad.fabric.databinding.FabricRowBinding

class fabricAdapter(val data:ArrayList<fabricData>): RecyclerView.Adapter<fabricAdapter.ViewHolder>() {
    lateinit var parentView: View

    var itemClickListener:OnItemClickListener?=null
    var imageClickListener:OnLongClickListener?=null
    var chartClickListener:OnChartClickListener?=null
    interface OnItemClickListener{
        fun OnItemClick(fData:fabricData)
    }
    interface OnLongClickListener{
        fun onItemLongClick(fData: fabricData)
    }
    interface OnChartClickListener{
        fun onChartClick(fData: fabricData)
    }
    inner class ViewHolder(val binding: FabricRowBinding): RecyclerView.ViewHolder(binding.root){
        init{
            binding.constraintLayout.setOnClickListener {
                itemClickListener?.OnItemClick(data[adapterPosition])
            }
            binding.imageView5.setOnLongClickListener {
                imageClickListener?.onItemLongClick(data[adapterPosition])
                true
            }
            binding.chartButton.setOnClickListener {
                chartClickListener?.onChartClick(data[adapterPosition])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = FabricRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        parentView = parent.rootView
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("test",data[position].completeDate.toString())
        if(data[position].completeDate.toString().equals("null")){
            holder.binding.dateView.text = data[position].date
            holder.binding.imageView4.setImageResource(R.drawable.baseline_hourglass_top_24)
        }
        else{
            holder.binding.dateView.text = data[position].completeDate
            if(data[position].d_cnt<=5) {
                holder.binding.imageView4.setImageResource(R.drawable.baseline_check_24)
            }else{
                holder.binding.imageView4.setImageResource(R.drawable.baseline_warning_24)
            }
        }
        holder.binding.idView.text = data[position].id
        holder.binding.defectView.text = data[position].d_cnt.toString()
        holder.binding.totalView.text = String.format("%.2f",(data[position].total_cnt.toDouble()/(91.44).toDouble()))
        Glide.with(parentView).load("http://49.173.62.69:8080/"+data[position].image_path).into(holder.binding.imageView5)//).into()
        holder.binding.eRateView.text = String.format("%.2f",(data[position].d_cnt.toDouble() / (data[position].total_cnt.toDouble()/(91.44).toDouble())))
    }
}