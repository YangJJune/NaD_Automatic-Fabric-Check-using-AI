package nad.fabric

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nad.fabric.databinding.DefectRowBinding

class defectAdapter(val data:ArrayList<defectData>): RecyclerView.Adapter<defectAdapter.ViewHolder>() {
    interface OnItemClickListener{
        fun OnItemClick(fData:defectData)
    }
    inner class ViewHolder(val binding: DefectRowBinding): RecyclerView.ViewHolder(binding.root){
        init{
//            binding.constraintLayout.setOnClickListener {
//                itemClickListener?.OnItemClick(data[adapterPosition])
//            }
        }
    }
    var itemClickListener:OnItemClickListener?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DefectRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.issueName.text = data[position].issue_name
        holder.binding.errorName.text = data[position].id
        holder.binding.testDate.text = data[position].date
    }
}