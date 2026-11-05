package com.eraorder.arena.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.eraorder.arena.R
import com.eraorder.arena.domain.model.DateFormat
import com.eraorder.arena.domain.model.Region
import kotlin.math.abs

@Composable
fun eventDateText(year: Int, format: DateFormat): String {
    val value = abs(year)
    val resource = when {
        year < 0 && format == DateFormat.BCE_CE -> R.string.date_bce
        year < 0 -> R.string.date_bc
        format == DateFormat.BCE_CE -> R.string.date_ce
        else -> R.string.date_ad
    }
    return stringResource(resource, value)
}

@Composable
fun centuryText(century: Int, format: DateFormat): String {
    val value = abs(century) * 100
    val resource = when {
        century < 0 && format == DateFormat.BCE_CE -> R.string.century_bce
        century < 0 -> R.string.century_bc
        format == DateFormat.BCE_CE -> R.string.century_ce
        else -> R.string.century_ad
    }
    return stringResource(resource, value)
}

@Composable
fun regionLabel(region: Region): String = stringResource(
    when (region) {
        Region.EUROPE -> R.string.region_europe
        Region.ASIA -> R.string.region_asia
        Region.AFRICA -> R.string.region_africa
        Region.AMERICAS -> R.string.region_americas
        Region.OCEANIA -> R.string.region_oceania
    }
)

@Composable
fun spriteResource(key: String): Int = when (key) {
    "sprite_1" -> R.drawable.sprite_1
    "sprite_2" -> R.drawable.sprite_2
    "sprite_3" -> R.drawable.sprite_3
    "sprite_4" -> R.drawable.sprite_4
    else -> R.drawable.sprite_5
}
