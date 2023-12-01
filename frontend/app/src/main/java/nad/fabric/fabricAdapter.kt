package nad.fabric

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nad.fabric.databinding.FabricRowBinding

class fabricAdapter(val data:ArrayList<fabricData>): RecyclerView.Adapter<fabricAdapter.ViewHolder>() {
    lateinit var parentView: View
    interface OnItemClickListener{
        fun OnItemClick(fData:fabricData)
    }
    inner class ViewHolder(val binding: FabricRowBinding): RecyclerView.ViewHolder(binding.root){
        init{
            binding.constraintLayout.setOnClickListener {
                itemClickListener?.OnItemClick(data[adapterPosition])
            }
        }
    }
    var itemClickListener:OnItemClickListener?=null


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
            holder.binding.dateView.text = data[position].completeDate
            holder.binding.imageView4.setImageResource(R.drawable.baseline_hourglass_top_24)
        }
        else{
            holder.binding.dateView.text = data[position].date
        }
        holder.binding.idView.text = data[position].id
        holder.binding.defectView.text = String.format("%.2f",(data[position].d_cnt.toDouble()/(91.44).toDouble()))
        holder.binding.totalView.text = String.format("%.2f",(data[position].total_cnt.toDouble()/(91.44).toDouble()))
        Glide.with(parentView).load("http://49.173.62.69:8080/20231124_130338.jpg").into(holder.binding.imageView5)//data[position].image_path).into()
        holder.binding.eRateView.text = String.format("%.2f",(data[position].d_cnt.toDouble() / data[position].total_cnt.toDouble())*100) + "%"
    }
}