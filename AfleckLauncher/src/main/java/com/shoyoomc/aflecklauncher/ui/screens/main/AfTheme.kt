package com.shoyoomc.aflecklauncher.ui.screens.main

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.toArgb
import com.shoyoomc.aflecklauncher.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Definição de um tema aurora. */
class AfThemeDef(
    val id: String,
    val name: String,
    val bg: Int,
    val accent: Color,
    val accent2: Color,
    val base: Color,
    val border: Color,
    val dim: Color
)

/** Estado do tema (cores, vibração, contraste, opacidade), salvo no aparelho. */
object AfThemeState {
    val themes = listOf(
        AfThemeDef("branca", "Aurora Branca", R.drawable.af_bg_branca, Color(0xFFFFFFFF), Color(0xFFBFC8D2), Color(0xFF0B0B0D), Color(0xFFE6ECF2), Color(0xFFB4B9C2)),
        AfThemeDef("ciano", "Aurora Ciano", R.drawable.af_bg_ciano, Color(0xFF22E3F7), Color(0xFF4DF2D8), Color(0xFF0A3558), Color(0xFF1ED8F5), Color(0xFFB9D6E6)),
        AfThemeDef("verde", "Aurora Verde", R.drawable.af_bg_verde, Color(0xFF3DFF8A), Color(0xFFB6FF5E), Color(0xFF07261A), Color(0xFF2BE67A), Color(0xFFB4DCC4)),
        AfThemeDef("roxa", "Aurora Roxa", R.drawable.af_bg_roxa, Color(0xFFB388FF), Color(0xFFFF7AE0), Color(0xFF1E0B3A), Color(0xFFB388FF), Color(0xFFD0C2E8)),
        AfThemeDef("amarela", "Aurora Amarela", R.drawable.af_bg_amarela, Color(0xFFFFD740), Color(0xFFFF9F1C), Color(0xFF2A1E05), Color(0xFFFFC93C), Color(0xFFE6D8B0)),
        AfThemeDef("rosa", "Aurora Rosa", R.drawable.af_bg_rosa, Color(0xFFFF6EA8), Color(0xFFFFB36E), Color(0xFF3A0B22), Color(0xFFFF5C9A), Color(0xFFE8C2D2))
    )

    var themeId by mutableStateOf("branca")
        private set
    var vibrance by mutableIntStateOf(0)
        private set
    var contrast by mutableIntStateOf(0)
        private set
    var panelOpacity by mutableIntStateOf(92)
        private set
    var animate by mutableStateOf(false)
        private set

    private var prefs: SharedPreferences? = null

    fun load(ctx: Context) {
        if (prefs != null) return
        val p = ctx.getSharedPreferences("afleck_ui", Context.MODE_PRIVATE)
        prefs = p
        themeId = p.getString("theme.id", "branca") ?: "branca"
        vibrance = p.getInt("theme.vibrance", 0)
        contrast = p.getInt("theme.contrast", 0)
        panelOpacity = p.getInt("theme.panelOpacity", 92)
        animate = p.getBoolean("theme.animate", false)
    }

    fun chooseTheme(id: String) { themeId = id; saveAll() }
    fun changeVibrance(v: Int) { vibrance = v.coerceIn(-100, 100); saveAll() }
    fun changeContrast(v: Int) { contrast = v.coerceIn(-100, 100); saveAll() }
    fun changePanelOpacity(v: Int) { panelOpacity = v.coerceIn(30, 100); saveAll() }
    fun changeAnimate(v: Boolean) { animate = v; saveAll() }
    fun resetAdjustments() { vibrance = 0; contrast = 0; panelOpacity = 92; animate = false; saveAll() }

    private fun saveAll() {
        prefs?.edit()
            ?.putString("theme.id", themeId)
            ?.putInt("theme.vibrance", vibrance)
            ?.putInt("theme.contrast", contrast)
            ?.putInt("theme.panelOpacity", panelOpacity)
            ?.putBoolean("theme.animate", animate)
            ?.apply()
    }

    val current: AfThemeDef get() = themes.firstOrNull { it.id == themeId } ?: themes[0]

    private fun vivid(c: Color): Color {
        val hsv = FloatArray(3)
        android.graphics.Color.colorToHSV(c.toArgb(), hsv)
        hsv[1] = (hsv[1] * (1f + vibrance / 100f)).coerceIn(0f, 1f)
        return Color(android.graphics.Color.HSVToColor(hsv))
    }

    private fun contrasted(c: Color): Color =
        if (contrast >= 0) lerp(c, Color.Black, 0.45f * contrast / 100f)
        else lerp(c, Color.White, 0.25f * -contrast / 100f)

    val accent: Color get() = vivid(current.accent)
    val accent2: Color get() = vivid(current.accent2)
    val border: Color get() = vivid(current.border)
    val base: Color get() = contrasted(current.base)
}

internal val AfPanel: Color get() = AfThemeState.base.copy(alpha = AfThemeState.panelOpacity / 100f)
internal val AfPanelDeep: Color get() = AfThemeState.base.copy(alpha = (AfThemeState.panelOpacity / 100f + 0.05f).coerceAtMost(1f))
internal val AfBorder: Color get() = AfThemeState.border
internal val AfAccent: Color get() = AfThemeState.accent
internal val AfAccent2: Color get() = AfThemeState.accent2
internal val AfNavy: Color get() = lerp(AfThemeState.base, Color.Black, 0.55f).copy(alpha = 1f)
internal val AfText = Color.White
internal val AfDim: Color get() = AfThemeState.current.dim

/** Degradê dos botões; se "Animar degradê" estiver ligado, as cores se movem. */
@Composable
internal fun afAccentBrush(): Brush {
    if (!AfThemeState.animate) return Brush.horizontalGradient(listOf(AfAccent, AfAccent2))
    val t = rememberInfiniteTransition(label = "afgrad")
    val off by t.animateFloat(0f, 800f, infiniteRepeatable(tween(4000, easing = LinearEasing)), label = "afoff")
    return Brush.horizontalGradient(listOf(AfAccent, AfAccent2), startX = off, endX = off + 400f, tileMode = TileMode.Mirror)
}

internal fun Modifier.afClick(onClick: () -> Unit): Modifier = composed {
    clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick)
}

/** Painel de vidro azul-marinho com borda ciano. */
@Composable
internal fun AfPanel(
    modifier: Modifier = Modifier,
    radius: Dp = 28.dp,
    deep: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(radius)
    Box(
        modifier = modifier
            .clip(shape)
            .background(if (deep) AfPanelDeep else AfPanel)
            .border(BorderStroke(1.5.dp, AfBorder), shape),
        content = content
    )
}

/** Botão principal: degradê ciano, texto escuro. */
@Composable
internal fun AfButton(text: String, modifier: Modifier = Modifier, icon: Int? = null, onClick: () -> Unit) {
    Row(
        modifier = modifier.height(50.dp).clip(CircleShape)
            .background(afAccentBrush())
            .afClick(onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (icon != null) {
            Icon(painterResource(icon), null, tint = AfNavy, modifier = Modifier.size(22.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, color = AfNavy, fontSize = 15.sp, fontWeight = FontWeight.Bold, maxLines = 1)
    }
}

/** Botão secundário: só contorno ciano. */
@Composable
internal fun AfOutlineButton(text: String, modifier: Modifier = Modifier, icon: Int? = null, onClick: () -> Unit) {
    Row(
        modifier = modifier.height(44.dp).clip(CircleShape)
            .background(AfNavy.copy(alpha = 0.5f))
            .border(1.5.dp, AfBorder, CircleShape)
            .afClick(onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (icon != null) {
            Icon(painterResource(icon), null, tint = AfAccent, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, color = AfText, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1)
    }
}

/** Círculo com ícone (preenchido em ciano ou escuro com contorno). */
@Composable
internal fun AfIconCircle(icon: Int, size: Dp = 40.dp, filled: Boolean = false, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(size).clip(CircleShape)
            .background(if (filled) afAccentBrush() else Brush.horizontalGradient(listOf(AfNavy, AfNavy)))
            .then(if (filled) Modifier else Modifier.border(1.5.dp, AfBorder, CircleShape)),
        contentAlignment = Alignment.Center
    ) {
        Icon(painterResource(icon), null, tint = if (filled) AfNavy else AfText, modifier = Modifier.size(size * 0.5f))
    }
}
