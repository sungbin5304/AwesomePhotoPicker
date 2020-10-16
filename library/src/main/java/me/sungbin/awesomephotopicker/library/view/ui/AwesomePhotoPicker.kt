package me.sungbin.awesomephotopicker.library.view.ui


/**
 * Created by SungBin on 2020-10-16.
 */

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_content.*
import me.sungbin.awesomephotopicker.library.R
import me.sungbin.awesomephotopicker.library.adapter.PhotoAdapter
import me.sungbin.awesomephotopicker.library.model.Tile
import me.sungbin.awesomephotopicker.library.model.TileType
import me.sungbin.awesomephotopicker.library.util.GridSpacingItemDecoration
import me.sungbin.awesomephotopicker.library.util.PhotoFilter
import me.sungbin.awesomephotopicker.library.util.PhotoUtil
import kotlin.properties.Delegates


class AwesomePhotoPicker : BottomSheetDialogFragment() {

    private var pickerHeight by Delegates.notNull<Float>()
    private lateinit var photoFilter: PhotoFilter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.layout_content, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cl_container.maxHeight = (getPopupHeight(pickerHeight) + 1)

        dialog?.setOnShowListener { dialog ->

            val bottomSheetDialog = dialog as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!
            val behavior = BottomSheetBehavior.from(bottomSheet)

            initLayoutHeight()

            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slide: Float) = Unit
                override fun onStateChanged(bottomSheet: View, state: Int) {
                    when (state) {
                        BottomSheetBehavior.STATE_HIDDEN -> dismiss()
                        else -> Unit
                    }
                }

            })
        }

        val tiles = ArrayList<Tile>()
        tiles.add(Tile(null, TileType.GALLERY))
        PhotoUtil.getAllPath(requireContext()).map {
            tiles.add(Tile(it, TileType.PHOTO))
        }

        rv_gallery.apply {
            adapter = PhotoAdapter(tiles, requireActivity())
            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.margin_twice_half)
            addItemDecoration(GridSpacingItemDecoration(3, spacingInPixels, false, 0))
        }
    }

    private fun getPopupHeight(percent: Float): Int {
        val displayMetrics = DisplayMetrics()
        // todo: `context?.display?.getRealMetrics(DisplayMetrics())` <- why do not working this code?
        @Suppress("DEPRECATION")
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * percent).toInt()
    }

    private fun initLayoutHeight() {
        val bottomSheetDialog = dialog as BottomSheetDialog
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!

        val behavior = BottomSheetBehavior.from(bottomSheet)

        behavior.apply {
            skipCollapsed = true
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        val params = bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
        val maxHeight = getPopupHeight(pickerHeight)

        if (maxHeight < bottomSheet.height) {
            params.height = maxHeight
            bottomSheet.layoutParams = params
            behavior.peekHeight = maxHeight
        }
    }

    companion object {
        private lateinit var awesomePhotoPicker: AwesomePhotoPicker

        fun with(
            pickerHeight: Float = .85f,
            photoFilter: PhotoFilter = PhotoFilter()
        ): AwesomePhotoPicker {
            if (!::awesomePhotoPicker.isInitialized) {
                awesomePhotoPicker = AwesomePhotoPicker()
            }
            awesomePhotoPicker.pickerHeight = pickerHeight
            awesomePhotoPicker.photoFilter = photoFilter
            return awesomePhotoPicker
        }
    }

}