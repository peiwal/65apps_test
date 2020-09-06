package petrov.ivan.gb65apps.utils

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import petrov.ivan.gb65apps.R
import petrov.ivan.gb65apps.data.Speciality
import java.lang.StringBuilder

@BindingAdapter("imageEmployeeCircle")
fun ImageView.loadEmployeeImageCircle(imgUri: String?) {
    Glide.with(context)
        .load(imgUri)
        .apply(RequestOptions.circleCropTransform())
        .placeholder(R.drawable.ic_person)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}

@BindingAdapter("imageEmployee")
fun ImageView.loadEmployeeImage(imgUrl: String?) {
    Glide.with(context)
        .load(imgUrl)
        .placeholder(R.drawable.ic_person)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}

@BindingAdapter("textSpeciality")
fun TextView.setSpeciality(specialitys: List<Speciality>) {
    val builder = StringBuilder()

    val prefix = this.context.resources.getString(
        if (specialitys.size > 1) R.string.speciality_many_preffix
        else R.string.speciality_one_preffix)
    builder.append(prefix)

    specialitys.forEach {
        builder.append(it.name)
        if (it != specialitys.last()) {
            builder.append(", ")
        }
    }

    text = builder
}
