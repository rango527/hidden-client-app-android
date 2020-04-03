package com.hidden.client.ui.viewmodels.custom

import com.hidden.client.R
import com.hidden.client.helpers.Enums
import com.hidden.client.models.entity.DashboardTileContentEntity
import com.hidden.client.ui.viewmodels.root.RootVM

class DashboardNumberTileViewVM (
    private var tileContent: DashboardTileContentEntity,
    private var colorScheme: String
) : RootVM() {

    fun getTileContent(): DashboardTileContentEntity {
        return this.tileContent
    }

    fun getNumberTileBackground(): Int {
        return when(colorScheme) {
            Enums.TileColorScheme.DARK.value -> {
                R.drawable.panel_round_shadow_black
            }
            else -> {
                R.drawable.panel_round_shadow_white
            }
        }
    }

    fun getNumberTileTextColor(): Int {
        return when(colorScheme) {
            Enums.TileColorScheme.DARK.value -> {
                R.color.colorWhite_1
            }
            else -> {
                R.color.colorBlack_1
            }
        }
    }
}