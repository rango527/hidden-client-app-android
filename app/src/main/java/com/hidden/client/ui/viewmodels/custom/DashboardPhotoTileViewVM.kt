package com.hidden.client.ui.viewmodels.custom

import com.hidden.client.models.entity.DashboardTileContentEntity
import com.hidden.client.ui.viewmodels.root.RootVM

class DashboardPhotoTileViewVM (
    private var tileContent: DashboardTileContentEntity
) : RootVM() {

    fun getTileContent(): DashboardTileContentEntity {
        return this.tileContent
    }
}