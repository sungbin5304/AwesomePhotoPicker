package me.sungbin.awesomephotopicker.library.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import me.sungbin.awesomephotopicker.library.R
import me.sungbin.awesomephotopicker.library.databinding.LayoutTileBinding
import me.sungbin.awesomephotopicker.library.model.Tile
import me.sungbin.awesomephotopicker.library.model.TileType
import me.sungbin.awesomephotopicker.library.util.PhotoUtil


/**
 * Created by SungBin on 2020-07-20.
 */

class PhotoAdapter(
    private val items: List<Tile>,
    private val activity: Activity
) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    class ViewHolder(private val binding: LayoutTileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder(tile: Tile) {
            with(binding) {
                when (tile.type) {
                    TileType.PHOTO -> {
                        // ivTilePhoto.setImageURI(tile.uri)
                        Glide.with(ivTilePhoto.context)
                            .load(PhotoUtil.convertUriToPath(ivTilePhoto.context, tile.uri!!))
                            .thumbnail(0.1f)
                            .apply(
                                RequestOptions().centerCrop()
                                    .placeholder(R.drawable.ic_baseline_photo_library_24)
                                    .error(R.drawable.ic_baseline_error_24)
                            )
                            .into(ivTilePhoto)
                        // todo: 위 코드로 랙 및 오류 수정 가능
                    }
                    TileType.CAMERA -> {

                    }
                    TileType.GALLERY -> {
                        // ivTilePhoto.visibility = View.GONE
                        // ivTileGallery.visibility = View.VISIBLE
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.layout_tile, viewGroup, false
            )
        )

    override fun onBindViewHolder(@NonNull viewholder: ViewHolder, position: Int) {
        viewholder.bindViewHolder(items[position])
    }

    override fun getItemCount() = items.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}