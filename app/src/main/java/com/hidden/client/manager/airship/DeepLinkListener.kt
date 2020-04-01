package com.hidden.client.manager.airship

import com.hidden.client.helpers.HCGlobal
import com.urbanairship.UAirship
import com.urbanairship.actions.DeepLinkListener

class DeepLinkListener: DeepLinkListener {
    override fun onDeepLink(deepLink: String): Boolean {
        HCGlobal.getInstance().log("current deep link: $deepLink")
        return true
    }
}