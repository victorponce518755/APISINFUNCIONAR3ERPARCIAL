package alanis.jorge.dogapp

import alanis.jorge.dogapp.databinding.ItemDogBinding
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class DogViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemDogBinding.bind(view)

    fun bind (image: String){
        //toma la imagen y la pone en el image view y la hace real
        Picasso.get().load(image).into(binding.ivDog)
    }
}

//Ahora toca hacer el api service, este caso llamado APIService.kt y sera una interface