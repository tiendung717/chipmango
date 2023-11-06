package io.chipmango.ad.request

import com.google.android.gms.ads.AdRequest

internal object AdRequestFactory {
    fun create(): AdRequest {
        return AdRequest.Builder().build()
    }
}
