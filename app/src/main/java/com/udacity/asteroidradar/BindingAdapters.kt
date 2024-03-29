package com.udacity.asteroidradar

import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.dateUtils.toStringRepresentation
import com.udacity.asteroidradar.domain.NearEarthObject
import java.time.LocalDate

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("astronomicalUnitTextForStringValue")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: String) {
    val context = textView.context
    textView.text =
        String.format(context.getString(R.string.astronomical_unit_format_for_string_value), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, velocity: String) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), velocity)
}

@BindingAdapter("asteroidIdText")
fun bindTextViewToAsteroidId(textView: TextView, nearEarthObject: NearEarthObject) {
    val context = textView.context
    textView.text = String.format(
        context.getString(R.string.asteroid_id),
        nearEarthObject.id,
        nearEarthObject.codeName
    )
}

@BindingAdapter("closeApproachDateText")
fun bindTextViewToCloseApproachDate(textView: TextView, closeApproachDate: LocalDate) {
    textView.text = toStringRepresentation(closeApproachDate)
}

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("imageOfDayUrl")
fun bindPictureOfDay(mgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Picasso.get().load(imgUrl).into(mgView);
    }
}
