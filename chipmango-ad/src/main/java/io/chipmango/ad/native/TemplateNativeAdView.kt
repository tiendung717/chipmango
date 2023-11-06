package io.chipmango.ad.native

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import io.chipmango.ad.R

internal class TemplateNativeAdView(context: Context) : FrameLayout(context) {

    fun render(nativeAd: NativeAd, darkMode: Boolean) {
        val view = LayoutInflater.from(context).inflate(R.layout.template_native_default, null)
        val adView: NativeAdView = view.findViewById(R.id.nativeAdView)

        if (adView.headlineView == null) adView.headlineView = adView.findViewById(R.id.ad_headline)
        if (adView.bodyView == null) adView.bodyView = adView.findViewById(R.id.ad_body)
        if (adView.callToActionView == null) adView.callToActionView =
            adView.findViewById(R.id.ad_call_to_action)
        if (adView.iconView == null) adView.iconView = adView.findViewById(R.id.ad_app_icon)

        (adView.headlineView as TextView).text = nativeAd.headline
        (adView.headlineView as TextView).setTextColor(
            if (darkMode) ContextCompat.getColor(adView.context, R.color.ad_text_dark)
            else ContextCompat.getColor(adView.context, R.color.ad_text_light)
        )
        if (adView.bodyView != null) {
            if (nativeAd.body == null) {
                adView.bodyView?.visibility = GONE
            } else {
                adView.bodyView?.visibility = VISIBLE
                (adView.bodyView as TextView).text = nativeAd.body
                (adView.bodyView as TextView).setTextColor(
                    if (darkMode) ContextCompat.getColor(adView.context, R.color.ad_text_dark)
                    else ContextCompat.getColor(adView.context, R.color.ad_text_light)
                )
            }
        }

        if (adView.callToActionView != null) {
            if (nativeAd.callToAction == null) {
                adView.callToActionView?.visibility = GONE
            } else {
                adView.callToActionView?.visibility = VISIBLE
                (adView.callToActionView as TextView).text = nativeAd.callToAction
            }
        }

        if (adView.iconView != null) {
            if (nativeAd.icon == null) {
                adView.iconView?.visibility = GONE
            } else {
                (adView.iconView as ImageView).setImageDrawable(
                    nativeAd.icon?.drawable
                )
                adView.iconView?.visibility = VISIBLE
            }
        }

        adView.setNativeAd(nativeAd)
        removeAllViews()
        addView(view)
    }
}