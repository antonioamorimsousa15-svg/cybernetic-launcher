/*
 * Zalith Launcher 2
 * Copyright (C) 2025-2026 MovTery <movtery228@qq.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/gpl-3.0.txt>.
 */

package com.shoyoomc.cyberneticlauncher.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val base = Typography()
private fun TextStyle.sans(w: FontWeight? = null, ls: Float = 0f) =
    copy(fontFamily = FontFamily.SansSerif, fontWeight = w ?: fontWeight, letterSpacing = if (ls != 0f) ls.sp else letterSpacing)

/** CYBERNETIC: tipografia técnica — sem serifa, títulos firmes, rótulos com espaçamento. */
val AppTypography = Typography(
    displayLarge = base.displayLarge.sans(FontWeight.Black),
    displayMedium = base.displayMedium.sans(FontWeight.Black),
    displaySmall = base.displaySmall.sans(FontWeight.Black),
    headlineLarge = base.headlineLarge.sans(FontWeight.Bold),
    headlineMedium = base.headlineMedium.sans(FontWeight.Bold),
    headlineSmall = base.headlineSmall.sans(FontWeight.Bold),
    titleLarge = base.titleLarge.sans(FontWeight.Bold),
    titleMedium = base.titleMedium.sans(FontWeight.Bold),
    titleSmall = base.titleSmall.sans(FontWeight.Bold),
    bodyLarge = base.bodyLarge.sans(),
    bodyMedium = base.bodyMedium.sans(),
    bodySmall = base.bodySmall.sans(),
    labelLarge = base.labelLarge.sans(FontWeight.SemiBold, 0.8f),
    labelMedium = base.labelMedium.sans(FontWeight.SemiBold, 0.8f),
    labelSmall = base.labelSmall.sans(FontWeight.Medium, 0.6f),
)
