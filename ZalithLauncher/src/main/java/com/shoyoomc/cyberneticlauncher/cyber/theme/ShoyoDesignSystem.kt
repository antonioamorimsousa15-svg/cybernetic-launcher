package com.shoyoomc.cyberneticlauncher.cyber.theme

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.composed
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

// =====================================================================
//  PALETAS  (tema de cores do launcher — trocável em Ajustes › Aparência)
// =====================================================================
data class ShoyoPalette(
    val name: String,
    val accent: Color,
    val accentDeep: Color,
    val onAccent: Color,
    val secondary: Color,
    val secondaryDeep: Color,
    val textSecondary: Color,
    val bg: Color,
    val bgAlt: Color,
    val panelTop: Color,
    val panelBottom: Color,
    val glass: Color,
    val rowBg: Color,
    /** true = cantos arredondados (Cryogenic); false = paralelogramo (Industrial). */
    val rounded: Boolean = false
)

object ShoyoPalettes {
    val Glacier = ShoyoPalette(
        name = "Geleira",
        accent = Color(0xFF5CF7FF), accentDeep = Color(0xFF22D3EE), onAccent = Color(0xFF031418),
        secondary = Color(0xFFB69CFF), secondaryDeep = Color(0xFF7B5CF0),
        textSecondary = Color(0xFF9FD8E6),
        bg = Color(0xFF050B1A), bgAlt = Color(0xFF0A1A2E),
        panelTop = Color(0xE60A1A2E), panelBottom = Color(0xF2050B1A),
        glass = Color(0x990A1A2E), rowBg = Color(0xE60A0F24)
    )
    val Crimson = ShoyoPalette(
        name = "Vermelho Shoyo",
        accent = Color(0xFFFF4D5E), accentDeep = Color(0xFFD7263D), onAccent = Color(0xFF1A0408),
        secondary = Color(0xFFFFB84D), secondaryDeep = Color(0xFFE08A00),
        textSecondary = Color(0xFFE6A3AB),
        bg = Color(0xFF0A0305), bgAlt = Color(0xFF1E0A10),
        panelTop = Color(0xE61E0A10), panelBottom = Color(0xF20A0305),
        glass = Color(0x991E0A10), rowBg = Color(0xE6150609)
    )
    val Violet = ShoyoPalette(
        name = "Violeta",
        accent = Color(0xFFB69CFF), accentDeep = Color(0xFF7B5CF0), onAccent = Color(0xFF120A2A),
        secondary = Color(0xFF5CF7FF), secondaryDeep = Color(0xFF22D3EE),
        textSecondary = Color(0xFFC9BDF2),
        bg = Color(0xFF07051A), bgAlt = Color(0xFF140E2E),
        panelTop = Color(0xE6140E2E), panelBottom = Color(0xF207051A),
        glass = Color(0x99140E2E), rowBg = Color(0xE60C0820)
    )
    val Emerald = ShoyoPalette(
        name = "Esmeralda",
        accent = Color(0xFF4DF0A8), accentDeep = Color(0xFF10B981), onAccent = Color(0xFF03150D),
        secondary = Color(0xFFC8F560), secondaryDeep = Color(0xFF84CC16),
        textSecondary = Color(0xFF9FE6C8),
        bg = Color(0xFF04110C), bgAlt = Color(0xFF0A2218),
        panelTop = Color(0xE60A2218), panelBottom = Color(0xF204110C),
        glass = Color(0x990A2218), rowBg = Color(0xE6071A12)
    )

    val Cryogenic = ShoyoPalette(
        name = "Cryogenic",
        accent = Color(0xFF55CFFF), accentDeep = Color(0xFF2EC4B6), onAccent = Color(0xFF03111C),
        secondary = Color(0xFFFFA45C), secondaryDeep = Color(0xFFE07A2E),
        textSecondary = Color(0xFF9DBBD6),
        bg = Color(0xFF06122A), bgAlt = Color(0xFF0C2140),
        panelTop = Color(0xB3123058), panelBottom = Color(0xCC081A38),
        glass = Color(0x66184070), rowBg = Color(0x99102A4E),
        rounded = true
    )

    val Industrial = ShoyoPalette(
        name = "Industrial",
        accent = Color(0xFFFF7A1A), accentDeep = Color(0xFFE85D04), onAccent = Color(0xFF0A0500),
        secondary = Color(0xFFFFC21A), secondaryDeep = Color(0xFFD99A00),
        textSecondary = Color(0xFFC9A583),
        bg = Color(0xFF0A0807), bgAlt = Color(0xFF1A1410),
        panelTop = Color(0xE61A1410), panelBottom = Color(0xF20A0807),
        glass = Color(0x991A1410), rowBg = Color(0xE6120E0B)
    )

    val all = listOf(Cryogenic, Industrial, Crimson, Glacier, Violet, Emerald)
    fun byName(name: String): ShoyoPalette = all.firstOrNull { it.name == name } ?: Cryogenic
}

// =====================================================================
//  ESTADO DE UI + PREFERÊNCIAS (tudo que as telas de Ajustes controlam)
// =====================================================================
object ShoyoUi {
    // Aparência
    var paletteName by mutableStateOf("Cryogenic")   // legado (não usado)
    // Estúdio de temas
    var themeId by mutableStateOf("criogenico")
    var themeSat by mutableFloatStateOf(0f)
    var themeVib by mutableFloatStateOf(0f)
    var themeCon by mutableFloatStateOf(0f)
    var customA by mutableIntStateOf(0xFFB69CFF.toInt())
    var customB by mutableIntStateOf(0xFFFFB6F0.toInt())
    var gradAnim by mutableStateOf(false)
    var gradSpeed by mutableFloatStateOf(50f)
    var glowOn by mutableStateOf(true)
    var glowLevel by mutableFloatStateOf(6f)        // 1..10
    var glowIntensity by mutableFloatStateOf(80f)   // 5..100 (%)
    var fullscreen by mutableStateOf(true)

    // Papel de parede (imagem ou vídeo de até 32 s)
    var wallpaperPath by mutableStateOf("")
    var wallpaperIsVideo by mutableStateOf(false)
    var wallpaperOpacity by mutableFloatStateOf(60f)   // 0..100 (%)
    var wallpaperVolume by mutableFloatStateOf(50f)    // 0..100 (%)

    // Macro de cliques (survival)
    var macroEnabled by mutableStateOf(false)
    var macroSafeMode by mutableStateOf(true)
    var attackMinCps by mutableFloatStateOf(6f)
    var attackMaxCps by mutableFloatStateOf(8f)
    var placeMinCps by mutableFloatStateOf(6f)
    var placeMaxCps by mutableFloatStateOf(8f)

    // Jogo / gráficos
    var ramMb by mutableFloatStateOf(1536f)
    var resolutionScale by mutableFloatStateOf(100f)
    var language by mutableStateOf("Português")
    var renderer by mutableStateOf("GL4ES")
    var maxRefreshRate by mutableStateOf(true)

    // Toque
    var touchSensitivity by mutableFloatStateOf(100f)
    var gyroEnabled by mutableStateOf(false)
    var gyroSmoothing by mutableStateOf(true)
    var gyroWindow by mutableFloatStateOf(4f)
    var invertX by mutableStateOf(false)
    var invertY by mutableStateOf(false)

    // Contas / versão
    val accounts = mutableStateListOf("shoyo")
    var activeAccount by mutableStateOf("shoyo")
    var selectedVersion by mutableStateOf("1.8.9 Forge")
    val favorites = mutableStateListOf<String>()

    // Setup inicial
    var setupDone by mutableStateOf(false)

    val palette: ShoyoPalette get() = ThemeCatalog.paletteFor(themeId, themeSat, themeVib, themeCon, customA, customB)

    private const val FILE = "shoyo_ui"

    fun load(ctx: Context) {
        val p = ctx.getSharedPreferences(FILE, Context.MODE_PRIVATE)
        themeId = p.getString("themeId", themeId) ?: themeId
        themeSat = p.getFloat("themeSat", themeSat)
        themeVib = p.getFloat("themeVib", themeVib)
        themeCon = p.getFloat("themeCon", themeCon)
        customA = p.getInt("customA", customA)
        customB = p.getInt("customB", customB)
        gradAnim = p.getBoolean("gradAnim", gradAnim)
        gradSpeed = p.getFloat("gradSpeed", gradSpeed)
        glowOn = p.getBoolean("glowOn", glowOn)
        glowLevel = p.getFloat("glowLevel", glowLevel)
        glowIntensity = p.getFloat("glowIntensity", glowIntensity)
        fullscreen = p.getBoolean("fullscreen", fullscreen)
        wallpaperPath = p.getString("wpPath", wallpaperPath) ?: wallpaperPath
        wallpaperIsVideo = p.getBoolean("wpVideo", wallpaperIsVideo)
        wallpaperOpacity = p.getFloat("wpOpacity", wallpaperOpacity)
        wallpaperVolume = p.getFloat("wpVolume", wallpaperVolume)
        macroEnabled = p.getBoolean("macroOn", macroEnabled)
        macroSafeMode = p.getBoolean("macroSafe", macroSafeMode)
        attackMinCps = p.getFloat("atkMin", attackMinCps)
        attackMaxCps = p.getFloat("atkMax", attackMaxCps)
        placeMinCps = p.getFloat("plcMin", placeMinCps)
        placeMaxCps = p.getFloat("plcMax", placeMaxCps)
        ramMb = p.getFloat("ramMb", ramMb)
        resolutionScale = p.getFloat("resScale", resolutionScale)
        language = p.getString("language", language) ?: language
        renderer = p.getString("renderer", renderer) ?: renderer
        maxRefreshRate = p.getBoolean("maxHz", maxRefreshRate)
        touchSensitivity = p.getFloat("touchSens", touchSensitivity)
        gyroEnabled = p.getBoolean("gyro", gyroEnabled)
        gyroSmoothing = p.getBoolean("gyroSmooth", gyroSmoothing)
        gyroWindow = p.getFloat("gyroWindow", gyroWindow)
        invertX = p.getBoolean("invertX", invertX)
        invertY = p.getBoolean("invertY", invertY)
        selectedVersion = p.getString("version", selectedVersion) ?: selectedVersion
        setupDone = p.getBoolean("setupDone", setupDone)
        val saved = p.getString("accounts", null)
            ?.split("\n")?.map { it.trim() }?.filter { it.isNotEmpty() }
        if (!saved.isNullOrEmpty()) {
            accounts.clear(); accounts.addAll(saved)
        }
        activeAccount = p.getString("activeAccount", activeAccount)
            ?.takeIf { it in accounts } ?: accounts.first()
        val favs = p.getString("favorites", null)
            ?.split("\n")?.map { it.trim() }?.filter { it.isNotEmpty() }
        favorites.clear()
        if (favs != null) favorites.addAll(favs)
    }

    fun save(ctx: Context) {
        ctx.getSharedPreferences(FILE, Context.MODE_PRIVATE).edit()
            .putString("themeId", themeId)
            .putFloat("themeSat", themeSat)
            .putFloat("themeVib", themeVib)
            .putFloat("themeCon", themeCon)
            .putInt("customA", customA)
            .putInt("customB", customB)
            .putBoolean("gradAnim", gradAnim)
            .putFloat("gradSpeed", gradSpeed)
            .putBoolean("glowOn", glowOn)
            .putFloat("glowLevel", glowLevel)
            .putFloat("glowIntensity", glowIntensity)
            .putBoolean("fullscreen", fullscreen)
            .putString("wpPath", wallpaperPath)
            .putBoolean("wpVideo", wallpaperIsVideo)
            .putFloat("wpOpacity", wallpaperOpacity)
            .putFloat("wpVolume", wallpaperVolume)
            .putBoolean("macroOn", macroEnabled)
            .putBoolean("macroSafe", macroSafeMode)
            .putFloat("atkMin", attackMinCps)
            .putFloat("atkMax", attackMaxCps)
            .putFloat("plcMin", placeMinCps)
            .putFloat("plcMax", placeMaxCps)
            .putFloat("ramMb", ramMb)
            .putFloat("resScale", resolutionScale)
            .putString("language", language)
            .putString("renderer", renderer)
            .putBoolean("maxHz", maxRefreshRate)
            .putFloat("touchSens", touchSensitivity)
            .putBoolean("gyro", gyroEnabled)
            .putBoolean("gyroSmooth", gyroSmoothing)
            .putFloat("gyroWindow", gyroWindow)
            .putBoolean("invertX", invertX)
            .putBoolean("invertY", invertY)
            .putString("version", selectedVersion)
            .putBoolean("setupDone", setupDone)
            .putString("accounts", accounts.joinToString("\n"))
            .putString("activeAccount", activeAccount)
            .putString("favorites", favorites.joinToString("\n"))
            .apply()
    }
}

// =====================================================================
//  CORES (leem a paleta ativa — mudar a paleta recompõe a interface)
// =====================================================================
object ShoyoColors {
    val Accent: Color get() = ShoyoUi.palette.accent
    val AccentDeep: Color get() = ShoyoUi.palette.accentDeep
    val OnAccent: Color get() = ShoyoUi.palette.onAccent
    val Secondary: Color get() = ShoyoUi.palette.secondary
    val SecondaryDeep: Color get() = ShoyoUi.palette.secondaryDeep
    val TextSecondary: Color get() = ShoyoUi.palette.textSecondary
    val Bg: Color get() = ShoyoUi.palette.bg
    val BgAlt: Color get() = ShoyoUi.palette.bgAlt
    // Com papel de parede ativo, painéis ficam mais transparentes para a arte aparecer.
    private fun see(c: Color): Color =
        if (ShoyoUi.wallpaperPath.isNotEmpty()) c.copy(alpha = c.alpha * 0.62f) else c
    val PanelTop: Color get() = see(ShoyoUi.palette.panelTop)
    val PanelBottom: Color get() = see(ShoyoUi.palette.panelBottom)
    val Glass: Color get() = see(ShoyoUi.palette.glass)
    val RowBg: Color get() = see(ShoyoUi.palette.rowBg)
    val PanelBorder: Color get() = ShoyoUi.palette.accent.copy(alpha = 0.45f)
    val IconChip: Color get() = ShoyoUi.palette.accent.copy(alpha = 0.25f)
    val TextPrimary: Color = Color(0xFFFFFFFF)
}

object ShoyoAccent {
    val Danger = Color(0xFFFF5C7A)
    val DangerBg = Color(0xFF1E0A14)
}

private val ShoyoTypography = Typography(
    titleMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold, fontSize = 17.sp),
    bodyMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 14.sp),
    labelSmall = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 10.sp, letterSpacing = 1.5.sp)
)

@Composable
fun ShoyoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = ShoyoColors.Accent,
            onPrimary = ShoyoColors.OnAccent,
            background = ShoyoColors.Bg,
            surface = ShoyoColors.PanelTop,
            onSurface = ShoyoColors.TextPrimary
        ),
        typography = ShoyoTypography,
        content = content
    )
}

// =====================================================================
//  FORMAS + BRILHO
// =====================================================================
val PanelShape: Shape get() = ParaShape(18.dp)
val PillShape: Shape get() = ParaShape(10.dp)

/** Clique sem ripple, com um leve "afundar" suave ao tocar (spring amortecida). */
fun Modifier.tap(onClick: () -> Unit): Modifier = composed {
    val source = remember { MutableInteractionSource() }
    val pressed by source.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = spring(dampingRatio = 0.8f, stiffness = 500f),
        label = "tapScale"
    )
    this
        .graphicsLayer { scaleX = scale; scaleY = scale }
        .clickable(interactionSource = source, indication = null, onClick = onClick)
}

/** Brilho neon ao redor da forma. Respeita o liga/desliga, o nível e a intensidade de Ajustes › Aparência. */
fun Modifier.neonGlow(shape: Shape, color: Color = ShoyoColors.Accent): Modifier {
    if (!ShoyoUi.glowOn) return this
    val glow = color.copy(alpha = (ShoyoUi.glowIntensity / 100f).coerceIn(0.05f, 1f))
    return this.shadow((ShoyoUi.glowLevel * 1.6f).dp, shape, ambientColor = glow, spotColor = glow)
}

// =====================================================================
//  COMPONENTES BASE
// =====================================================================

/** Painel PARALELOGRAMO com borda neon e segmentos de destaque nos cantos. */
@Composable
fun NeonPanel(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = modifier
            .clip(PanelShape)
            .background(Brush.verticalGradient(listOf(ShoyoColors.PanelTop, ShoyoColors.PanelBottom)))
            .hatch()
            .glassSheen()
            .border(1.dp, ShoyoColors.PanelBorder, PanelShape)
            .paraAccents(18.dp)
            .padding(
                start = if (ShoyoUi.palette.rounded) 20.dp else 30.dp,
                end = if (ShoyoUi.palette.rounded) 20.dp else 30.dp,
                top = 18.dp, bottom = 18.dp
            ),
        content = content
    )
}

/** Item do menu lateral. Cryogenic: pílula com ícone em círculo. Industrial: paralelogramo numerado escalonado. */
@Composable
fun SideNavItem(icon: ImageVector, label: String, selected: Boolean, onClick: () -> Unit, number: Int = 0, indent: Dp = 0.dp) {
    val rounded = ShoyoUi.palette.rounded
    val shape = ParaShape(if (rounded) 14.dp else 16.dp)
    val bg = if (selected) Brush.horizontalGradient(listOf(ShoyoColors.Accent, ShoyoColors.AccentDeep))
    else Brush.horizontalGradient(listOf(ShoyoColors.Glass, ShoyoColors.Glass))
    Row(
        modifier = Modifier
            .padding(start = if (rounded) 0.dp else indent)
            .fillMaxWidth()
            .then(if (selected) Modifier.neonGlow(shape) else Modifier)
            .clip(shape)
            .background(bg)
            .border(1.dp, ShoyoColors.PanelBorder, shape)
            .tap(onClick)
            .padding(
                start = if (rounded) 6.dp else 30.dp, end = 16.dp,
                top = if (rounded) 6.dp else 13.dp, bottom = if (rounded) 6.dp else 13.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (rounded) {
            Box(
                Modifier.size(32.dp).clip(ParaShape(7.dp))
                    .background(if (selected) ShoyoColors.Bg else ShoyoColors.IconChip),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = ShoyoColors.Accent, modifier = Modifier.size(18.dp))
            }
        } else {
            Text(
                "%02d".format(number), color = if (selected) ShoyoColors.OnAccent else ShoyoColors.Accent,
                fontFamily = FontFamily.Monospace, fontSize = 11.sp, fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.width(12.dp))
        Text(label, color = if (selected) ShoyoColors.OnAccent else ShoyoColors.TextPrimary,
            style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
    }
}

/** Aba numerada do topo: "01 Aparência", "02 Jogo"... */
@Composable
fun TopTab(index: Int, label: String, icon: ImageVector, selected: Boolean, onClick: () -> Unit) {
    val fg = if (selected) ShoyoColors.OnAccent else ShoyoColors.TextPrimary
    Row(
        modifier = Modifier
            .then(if (selected) Modifier.neonGlow(PillShape) else Modifier)
            .clip(PillShape)
            .background(if (selected) ShoyoColors.Accent else Color.Transparent)
            .tap(onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon, null, tint = if (selected) ShoyoColors.Accent else ShoyoColors.TextSecondary,
            modifier = Modifier.size(32.dp).clip(CircleShape)
                .background(if (selected) ShoyoColors.OnAccent else ShoyoColors.IconChip).padding(7.dp)
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text("%02d".format(index), color = if (selected) ShoyoColors.OnAccent else ShoyoColors.TextSecondary, style = MaterialTheme.typography.labelSmall)
            Text(label, color = fg, style = MaterialTheme.typography.titleMedium)
        }
    }
}

/** Botão principal (Instalar, Importar ModPack, Jogar...). */
@Composable
fun NeonButton(text: String, icon: ImageVector? = null, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .neonGlow(PillShape)
            .clip(PillShape)
            .background(Brush.horizontalGradient(listOf(ShoyoColors.Accent, ShoyoColors.AccentDeep)))
            .tap(onClick)
            .padding(horizontal = 28.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(icon, null, tint = ShoyoColors.OnAccent); Spacer(Modifier.width(8.dp))
        }
        Text(text.uppercase(), color = ShoyoColors.OnAccent, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
    }
}

/** Botão secundário (Cancelar, Recarregar...). */
@Composable
fun GhostButton(text: String, icon: ImageVector? = null, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .clip(PillShape)
            .background(ShoyoColors.Glass)
            .border(1.dp, ShoyoColors.PanelBorder, PillShape)
            .tap(onClick)
            .padding(horizontal = 28.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(icon, null, tint = ShoyoColors.Accent); Spacer(Modifier.width(8.dp))
        }
        Text(text.uppercase(), color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
    }
}

/** Chip de tag (Fabric, Otimização...). */
@Composable
fun TagChip(text: String) {
    Text(
        text, color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.clip(PillShape).border(1.dp, ShoyoColors.PanelBorder, PillShape)
            .padding(horizontal = 18.dp, vertical = 4.dp)
    )
}

/** Chip selecionável (temas de cores, plataforma, ordenação). */
@Composable
fun SelectChip(text: String, selected: Boolean, onClick: () -> Unit) {
    Text(
        text,
        color = if (selected) ShoyoColors.OnAccent else ShoyoColors.TextPrimary,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .then(if (selected) Modifier.neonGlow(PillShape) else Modifier)
            .clip(PillShape)
            .background(if (selected) ShoyoColors.Accent else ShoyoColors.Glass)
            .border(1.dp, ShoyoColors.PanelBorder, PillShape)
            .tap(onClick)
            .padding(horizontal = 26.dp, vertical = 10.dp)
    )
}

/** Slider próprio (sem depender da API do Material): trilho neon + thumb hexagonal. */
@Composable
fun NeonSlider(value: Float, onValueChange: (Float) -> Unit, range: ClosedFloatingPointRange<Float>, modifier: Modifier = Modifier) {
    val span = (range.endInclusive - range.start).coerceAtLeast(0.000001f)
    val frac = ((value - range.start) / span).coerceIn(0f, 1f)
    val accent = ShoyoColors.Accent
    val thumbPx = with(LocalDensity.current) { 30.dp.toPx() }
    var widthPx by remember { mutableFloatStateOf(1f) }

    Box(
        modifier
            .height(40.dp)
            .onSizeChanged { widthPx = it.width.toFloat().coerceAtLeast(1f) }
            .pointerInput(range) {
                detectTapGestures { o ->
                    onValueChange(range.start + (o.x / size.width.toFloat()).coerceIn(0f, 1f) * span)
                }
            }
            .pointerInput(range) {
                detectHorizontalDragGestures { change, _ ->
                    change.consume()
                    onValueChange(range.start + (change.position.x / size.width.toFloat()).coerceIn(0f, 1f) * span)
                }
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Canvas(Modifier.fillMaxWidth().height(10.dp)) {
            val r = CornerRadius(size.height / 2f)
            drawRoundRect(accent.copy(alpha = 0.25f), size = size, cornerRadius = r)
            drawRoundRect(accent, size = Size(size.width * frac, size.height), cornerRadius = r)
        }
        Box(
            Modifier.offset {
                IntOffset(
                    (frac * widthPx - thumbPx / 2f).coerceIn(0f, (widthPx - thumbPx).coerceAtLeast(0f)).toInt(), 0
                )
            }
        ) { HexThumb() }
    }
}

@Composable
private fun HexThumb() {
    val fill = ShoyoColors.Bg
    val line = ShoyoColors.Accent
    Canvas(Modifier.size(30.dp)) {
        val r = size.minDimension / 2f
        val c = Offset(size.width / 2f, size.height / 2f)
        val path = Path().apply {
            for (i in 0 until 6) {
                val a = PI / 3 * i - PI / 6
                val p = Offset(c.x + (r * cos(a)).toFloat(), c.y + (r * sin(a)).toFloat())
                if (i == 0) moveTo(p.x, p.y) else lineTo(p.x, p.y)
            }
            close()
        }
        drawPath(path, fill)
        drawPath(path, line, style = Stroke(width = 3f))
    }
}
