import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

object ImageUtils {
    interface ImageLoadCallback {
        fun onSuccess()
        fun onError()
    }

    fun loadImageFromUrl(url: String, imageView: ImageView, callback: ImageLoadCallback?) {
        val target = object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                imageView.setImageBitmap(bitmap)
                callback?.onSuccess()
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                imageView.setImageDrawable(errorDrawable)
                callback?.onError()
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                imageView.setImageDrawable(placeHolderDrawable)
            }
        }

        Picasso.get()
            .load(url)
            .into(target)
    }
}
