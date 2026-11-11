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

package com.shoyoomc.cyberneticlauncher.ui.screens.content.settings.layouts

import androidx.compose.foundation.layout.Arrangement
import com.shoyoomc.cyberneticlauncher.cyber.theme.techFrame
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shoyoomc.cyberneticlauncher.cyber.theme.ParaShape
import com.shoyoomc.cyberneticlauncher.ui.components.BackgroundCard
import com.shoyoomc.cyberneticlauncher.ui.components.TitleAndSummary

/**
 * 根据卡片在UI组中的位置，选择不同的圆角形状
 */
enum class CardPosition {
    /**
     * 位于 UI 组的顶部
     * ``` txt
     *   _______
     *  +       +
     * |         |
     * |         |
     * |         |
     *  ---------
     * ```
     */
    Top,

    /**
     * 位于 UI 组的顶部左侧
     * ``` txt
     *   ________
     *  +        |
     * |         |
     * |         |
     * |         |
     *  ---------
     * ```
     */
    TopStart,

    /**
     * 位于 UI 组的顶部右侧
     * ``` txt
     *  ________
     * |        +
     * |         |
     * |         |
     * |         |
     *  ---------
     * ```
     */
    TopEnd,

    /**
     * 位于 UI 组的中部
     * ``` txt
     *  _________
     * |         |
     * |         |
     * |         |
     * |         |
     *  ---------
     * ```
     */
    Middle,

    /**
     * 位于 UI 组的底部
     * ``` txt
     *  _________
     * |         |
     * |         |
     * |         |
     *  +       +
     *   -------
     * ```
     */
    Bottom,

    /**
     * 位于 UI 组的底部左侧
     * ``` txt
     *  _________
     * |         |
     * |         |
     * |         |
     *  +        |
     *   --------
     * ```
     */
    BottomStart,

    /**
     * 位于 UI 组的底部右侧
     * ``` txt
     *  _________
     * |         |
     * |         |
     * |         |
     * |        +
     *  --------
     * ```
     */
    BottomEnd,

    /**
     * 单个 UI 组件
     * ``` txt
     *   _______
     *  +       +
     * |         |
     * |         |
     *  +       +
     *   -------
     * ```
     */
    Single
}

/**
 * 根据 UI 组件在组中的位置决定的形状
 */
@Composable
fun rememberSettingsCardShape(
    position: CardPosition,
    outerShape: Dp = 28.dp,
    innerShape: Dp = 4.dp
): Shape {
    return remember { ParaShape(12.dp) }
}

@Composable
fun SettingsCard(
    modifier: Modifier = Modifier,
    position: CardPosition,
    outerShape: Dp = 28.dp,
    innerShape: Dp = 4.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = rememberSettingsCardShape(position, outerShape, innerShape)

    SettingsBackground(
        modifier = modifier,
        shape = shape,
        position = position,
        content = { Column(Modifier.padding(horizontal = 12.dp), content = content) }
    )
}

@Composable
fun SettingsCard(
    modifier: Modifier = Modifier,
    position: CardPosition,
    outerShape: Dp = 28.dp,
    innerShape: Dp = 4.dp,
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = rememberSettingsCardShape(position, outerShape, innerShape)

    SettingsBackground(
        modifier = modifier,
        shape = shape,
        position = position,
        onClick = onClick,
        enabled = enabled,
        content = { Column(Modifier.padding(horizontal = 12.dp), content = content) }
    )
}

@Composable
fun SettingsCard(
    position: CardPosition,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    titleStyle: TextStyle = MaterialTheme.typography.titleSmall,
    summaryStyle: TextStyle = MaterialTheme.typography.labelSmall,
    outerShape: Dp = 28.dp,
    innerShape: Dp = 4.dp,
    innerPadding: PaddingValues = PaddingValues(start = 28.dp, end = 28.dp, top = 16.dp, bottom = 16.dp),
    onClick: () -> Unit,
    trailingIcon: (@Composable RowScope.() -> Unit)? = null,
    enabled: Boolean = true
) {
    val shape = rememberSettingsCardShape(position, outerShape, innerShape)

    SettingsBackground(
        modifier = modifier,
        shape = shape,
        position = position,
        onClick = onClick,
        enabled = enabled
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TitleAndSummary(
                modifier = Modifier.weight(1f),
                title = title,
                summary = summary,
                titleStyle = titleStyle,
                summaryStyle = summaryStyle
            )
            trailingIcon?.let { trailing ->
                Row(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    content = trailing
                )
            }
        }
    }
}

@Composable
private fun settingsCardBorder() =
    androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.40f))

/** Dentro de um [SettingsCardColumn] os cartões viram linhas de um único painel de vidro, separadas por fios. */
val LocalSettingsGroup = androidx.compose.runtime.compositionLocalOf { false }

@Composable
private fun SettingsBackground(
    modifier: Modifier,
    shape: Shape,
    position: CardPosition,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    if (LocalSettingsGroup.current) {
        Column(modifier) {
            if (position != CardPosition.Top) {
                androidx.compose.foundation.layout.Box(
                    Modifier.fillMaxWidth().padding(horizontal = 14.dp).height(1.dp)
                        .background(com.shoyoomc.cyberneticlauncher.cyber.theme.ShoyoColors.PanelBorder.copy(alpha = 0.35f))
                )
            }
            Column(
                if (onClick != null) Modifier.clickable(enabled = enabled, onClick = onClick) else Modifier,
                content = content
            )
        }
    } else if (onClick != null) {
        BackgroundCard(modifier = modifier, shape = shape, border = settingsCardBorder(), onClick = onClick, enabled = enabled, content = content)
    } else {
        BackgroundCard(modifier = modifier, shape = shape, border = settingsCardBorder(), content = content)
    }
}

@Composable
fun SettingsCardColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val panel = remember { ParaShape(24.dp) }
    androidx.compose.runtime.CompositionLocalProvider(LocalSettingsGroup provides true) {
        Column(
            modifier = modifier
                .clip(panel)
                .background(com.shoyoomc.cyberneticlauncher.cyber.theme.ShoyoColors.Glass)
                .border(1.dp, com.shoyoomc.cyberneticlauncher.cyber.theme.ShoyoColors.PanelBorder, panel)
                .techFrame(),
            content = content
        )
    }
}
