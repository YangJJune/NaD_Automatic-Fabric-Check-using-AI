package nad.fabric

import java.io.Serializable

data class fabricData(val id:String, val date:String, val d_cnt:Int, val total_cnt:Int, val completeDate:String?, val image_path:String?):
    Serializable {

}
