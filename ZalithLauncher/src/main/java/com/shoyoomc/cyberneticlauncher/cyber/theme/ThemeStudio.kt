package com.shoyoomc.cyberneticlauncher.cyber.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlin.math.roundToInt

// =====================================================================
//  CATÁLOGO DE TEMAS — todos em estilo Aurora (brilho + degradê da cor do tema)
// =====================================================================
data class CyberTheme(
    val id: String, val name: String, val sub: String,
    val a: Color, val b: Color,
    val isNew: Boolean = false,
    val legacy: ShoyoPalette? = null
)

object ThemeCatalog {
    val themes: List<CyberTheme> = listOf(
        CyberTheme("criogenico", "Criogênico", "Degradê", Color(0xFF55CFFF), Color(0xFF2EC4B6)),
        CyberTheme("embermire", "Embermire", "Degradê", Color(0xFFFF8A3D), Color(0xFFFF4D6D)),
        CyberTheme("veludo", "Rosa de veludo", "Degradê", Color(0xFFFF6FA5), Color(0xFFB45CFF)),
        CyberTheme("nevoa", "Onda de névoa", "Degradê", Color(0xFF8F7BFF), Color(0xFF5CC8FF)),
        CyberTheme("geleira", "Geleira", "Degradê", Color(0xFFB5F0FF), Color(0xFF6BE3FF)),
        CyberTheme("crepusculo", "Crepúsculo Verdejante", "Degradê", Color(0xFF7BFF7B), Color(0xFFFFD15C)),
        CyberTheme("cinzas", "Cinzas Urbanas", "Degradê", Color(0xFFD9D9E8), Color(0xFF8A8FA3)),
        CyberTheme("amanhecer", "Amanhecer Silencioso", "Degradê", Color(0xFFFFE29A), Color(0xFFFF9AD5)),
        CyberTheme("vulcanico", "Vulcânico", "NOVO · degradê", Color(0xFFFF5E1A), Color(0xFFFFB000), true),
        CyberTheme("marinho", "Marinho", "NOVO · degradê", Color(0xFF2E6BFF), Color(0xFF22D3EE), true),
        CyberTheme("galaxia", "Galáxia", "NOVO · degradê", Color(0xFF8A3DFF), Color(0xFFFF3DCB), true),
        CyberTheme("verdejante", "Verdejante", "NOVO · degradê", Color(0xFF22E06B), Color(0xFF7CFF3D), true),
        // Arco-íris
        CyberTheme("rb-vermelho", "Arco-íris · Vermelho", "Aurora", Color(0xFFFF3B3B), Color(0xFFFF8A3D), true),
        CyberTheme("rb-laranja", "Arco-íris · Laranja", "Aurora", Color(0xFFFF9A1A), Color(0xFFFFD21A), true),
        CyberTheme("rb-amarelo", "Arco-íris · Amarelo", "Aurora", Color(0xFFFFE81A), Color(0xFFB6FF3D), true),
        CyberTheme("rb-verde", "Arco-íris · Verde", "Aurora", Color(0xFF1AE65C), Color(0xFF1AE6C4), true),
        CyberTheme("rb-ciano", "Arco-íris · Ciano", "Aurora", Color(0xFF1AD6FF), Color(0xFF1A8CFF), true),
        CyberTheme("rb-azul", "Arco-íris · Azul", "Aurora", Color(0xFF3D5CFF), Color(0xFF7B3DFF), true),
        CyberTheme("rb-violeta", "Arco-íris · Violeta", "Aurora", Color(0xFF9B3DFF), Color(0xFFFF3DE6), true),
        CyberTheme("rb-rosa", "Arco-íris · Rosa", "Aurora", Color(0xFFFF3D9B), Color(0xFFFF7A6B), true),
        CyberTheme("industrial", "Industrial", "Preto e laranja", Color(0xFFFF7A1A), Color(0xFFE85D04), false, ShoyoPalettes.Industrial),
        CyberTheme("custom", "Customizado", "Escolha sua cor", Color(0xFFB69CFF), Color(0xFFFFB6F0))
    )

    fun byId(id: String) = themes.firstOrNull { it.id == id } ?: themes.first()

    private fun adjust(c: Color, sat: Float, vib: Float, con: Float): Color {
        val hsv = FloatArray(3)
        android.graphics.Color.colorToHSV(c.toArgb(), hsv)
        var s = hsv[1]; var v = hsv[2]
        s = (s * (1f + sat / 100f)).coerceIn(0f, 1f)
        s = (s + (vib / 100f) * (1f - s) * 0.6f).coerceIn(0f, 1f)
        v = ((v - 0.5f) * (1f + con / 100f) + 0.5f).coerceIn(0.25f, 1f)
        return Color(android.graphics.Color.HSVToColor(floatArrayOf(hsv[0], s, v)))
    }

    private fun shiftHue(c: Color, deg: Float): Color {
        val hsv = FloatArray(3)
        android.graphics.Color.colorToHSV(c.toArgb(), hsv)
        hsv[0] = (hsv[0] + deg) % 360f
        return Color(android.graphics.Color.HSVToColor(hsv))
    }

    /** Monta a paleta completa (vidro + aurora) a partir de duas cores. */
    fun build(name: String, a: Color, b: Color): ShoyoPalette {
        val base = Color(0xFF050914)
        val bg = lerp(base, a, 0.10f)
        return ShoyoPalette(
            name = name,
            accent = a, accentDeep = b,
            onAccent = if (a.luminance() > 0.45f) Color(0xFF04101A) else Color.White,
            secondary = shiftHue(a, 40f), secondaryDeep = shiftHue(b, 40f),
            textSecondary = lerp(Color.White, a, 0.30f).copy(alpha = 0.85f),
            bg = bg, bgAlt = lerp(base, a, 0.20f),
            panelTop = lerp(bg, a, 0.16f).copy(alpha = 0.70f),
            panelBottom = bg.copy(alpha = 0.82f),
            glass = lerp(bg, a, 0.22f).copy(alpha = 0.42f),
            rowBg = lerp(bg, a, 0.14f).copy(alpha = 0.62f),
            rounded = true
        )
    }

    fun paletteFor(id: String, sat: Float, vib: Float, con: Float, customA: Int, customB: Int): ShoyoPalette {
        val t = byId(id)
        t.legacy?.let { return it }
        val (a, b) = if (id == "custom") Color(customA) to Color(customB) else t.a to t.b
        return build(t.name, adjust(a, sat, vib, con), adjust(b, sat, vib, con))
    }
}

private fun hueColor(h: Float) = Color(android.graphics.Color.HSVToColor(floatArrayOf(h, 0.75f, 1f)))
private fun hueOf(argb: Int): Float {
    val hsv = FloatArray(3); android.graphics.Color.colorToHSV(argb, hsv); return hsv[0]
}

// =====================================================================
//  ESTÚDIO DE TEMAS (diálogo)
// =====================================================================
@Composable
fun CyberThemeStudio(onDismiss: () -> Unit) {
    val ctx = LocalContext.current.applicationContext
    var selId by remember { mutableStateOf(ShoyoUi.themeId) }
    var sat by remember { mutableFloatStateOf(ShoyoUi.themeSat) }
    var vib by remember { mutableFloatStateOf(ShoyoUi.themeVib) }
    var con by remember { mutableFloatStateOf(ShoyoUi.themeCon) }
    var customA by remember { mutableIntStateOf(ShoyoUi.customA) }
    var customB by remember { mutableIntStateOf(ShoyoUi.customB) }
    var gradAnim by remember { mutableStateOf(ShoyoUi.gradAnim) }
    var gradSpeed by remember { mutableFloatStateOf(ShoyoUi.gradSpeed) }

    val preview = ThemeCatalog.paletteFor(selId, sat, vib, con, customA, customB)
    val applied = selId == ShoyoUi.themeId && sat == ShoyoUi.themeSat && vib == ShoyoUi.themeVib &&
        con == ShoyoUi.themeCon && (selId != "custom" || (customA == ShoyoUi.customA && customB == ShoyoUi.customB))
    val shape = ParaShape(22.dp)

    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Row(
            Modifier.fillMaxWidth(0.94f).fillMaxHeight(0.94f)
                .clip(shape).background(ShoyoColors.Bg.copy(alpha = 0.96f))
                .border(1.dp, ShoyoColors.PanelBorder, shape)
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // ---------- lista de temas ----------
            Column(Modifier.width(250.dp).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Estúdio de temas", color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleLarge)
                Text("Escolha o tema do Launcher", color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.labelMedium)
                LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(ThemeCatalog.themes) { t ->
                        val selected = t.id == selId
                        val a = if (t.id == "custom") Color(customA) else t.a
                        val b = if (t.id == "custom") Color(customB) else t.b
                        val rs = ParaShape(14.dp)
                        Row(
                            Modifier.fillMaxWidth().clip(rs)
                                .background(if (selected) ShoyoColors.Accent.copy(alpha = 0.20f) else ShoyoColors.Glass)
                                .border(1.dp, if (selected) ShoyoColors.Accent else ShoyoColors.PanelBorder, rs)
                                .tap { selId = t.id }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                Modifier.size(38.dp).clip(ParaShape(8.dp))
                                    .background(Brush.linearGradient(listOf(a, b))),
                                contentAlignment = Alignment.Center
                            ) {
                                if (selected) Icon(Icons.Filled.Check, null, tint = Color.Black.copy(alpha = 0.75f), modifier = Modifier.size(20.dp))
                            }
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text(t.name, color = ShoyoColors.TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1)
                                Text(t.sub, color = if (t.isNew) b else ShoyoColors.TextSecondary, fontSize = 11.sp, maxLines = 1)
                            }
                        }
                    }
                }
                GhostButton("Fechar", modifier = Modifier.fillMaxWidth(), onClick = onDismiss)
            }

            // ---------- prévia e ajustes ----------
            Column(
                Modifier.weight(1f).fillMaxHeight().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // prévia com as cores do tema escolhido (aurora)
                val ps = ParaShape(18.dp)
                Column(
                    Modifier.fillMaxWidth().clip(ps)
                        .background(preview.bg)
                        .drawBackgroundAurora(preview)
                        .border(1.dp, preview.accent.copy(alpha = 0.5f), ps)
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        repeat(3) { i ->
                            Column(
                                Modifier.weight(1f).height(54.dp).clip(ParaShape(12.dp))
                                    .background(preview.glass)
                                    .border(1.dp, preview.accent.copy(alpha = if (i == 1) 0.9f else 0.35f), ParaShape(12.dp))
                                    .padding(8.dp)
                            ) {
                                Box(Modifier.width(26.dp).height(4.dp).clip(ParaShape(3.dp)).background(preview.accent))
                                Spacer(Modifier.height(4.dp))
                                Box(Modifier.width(38.dp).height(4.dp).clip(ParaShape(3.dp)).background(preview.textSecondary))
                            }
                        }
                    }
                    Box(
                        Modifier.fillMaxWidth().height(10.dp).clip(ParaShape(5.dp))
                            .background(Brush.horizontalGradient(listOf(preview.accent, preview.accentDeep)))
                    )
                    Text(
                        if (applied) "● Tema em uso" else "● Prévia (toque em Aplicar tema)",
                        color = preview.accent, fontSize = 12.sp, fontWeight = FontWeight.Bold
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    NeonButton("Aplicar tema", Icons.Filled.Check, Modifier.weight(1f)) {
                        ShoyoUi.themeId = selId
                        ShoyoUi.themeSat = sat; ShoyoUi.themeVib = vib; ShoyoUi.themeCon = con
                        ShoyoUi.customA = customA; ShoyoUi.customB = customB
                        ShoyoUi.gradAnim = gradAnim; ShoyoUi.gradSpeed = gradSpeed
                        ShoyoUi.save(ctx)
                    }
                    GhostButton("Zerar ajustes", Icons.Filled.Refresh, Modifier.weight(1f)) {
                        sat = 0f; vib = 0f; con = 0f
                    }
                }

                StudioPanel("Ajustes de cor") {
                    AdjustRow("Saturação", sat) { sat = it }
                    AdjustRow("Vibração", vib) { vib = it }
                    AdjustRow("Contraste", con) { con = it }
                }

                if (selId == "custom") {
                    StudioPanel("Cor personalizada") {
                        HueRow("Cor 1", customA) { customA = it }
                        HueRow("Cor 2", customB) { customB = it }
                    }
                }

                StudioPanel("Degradê") {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text("Animar degradê", color = ShoyoColors.TextPrimary, fontWeight = FontWeight.Bold)
                            Text("Move o degradê do botão JOGAR. Deixe desligado para economizar bateria.", color = ShoyoColors.TextSecondary, fontSize = 12.sp)
                        }
                        Switch(checked = gradAnim, onCheckedChange = { gradAnim = it })
                    }
                    if (gradAnim) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Lento", color = ShoyoColors.TextSecondary, fontSize = 12.sp)
                            NeonSlider(gradSpeed, { gradSpeed = it.roundToInt().toFloat() }, 5f..100f, Modifier.weight(1f).padding(horizontal = 8.dp))
                            Text("Rápido  ${gradSpeed.roundToInt()}%", color = ShoyoColors.Accent, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }

                Text(
                    "A prévia muda na hora; o Launcher inteiro é atualizado quando você toca em Aplicar tema. Todos os temas usam o fundo Aurora com a cor escolhida.",
                    color = ShoyoColors.TextSecondary, fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth().clip(ParaShape(12.dp))
                        .border(1.dp, ShoyoColors.PanelBorder, ParaShape(12.dp)).padding(12.dp)
                )
            }
        }
    }
}

@Composable
private fun StudioPanel(title: String, content: @Composable () -> Unit) {
    val s = ParaShape(16.dp)
    Column(
        Modifier.fillMaxWidth().clip(s).background(ShoyoColors.Glass)
            .border(1.dp, ShoyoColors.PanelBorder, s).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(title.uppercase(), color = ShoyoColors.Accent, fontFamily = FontFamily.Monospace, fontSize = 11.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Bold)
        content()
    }
}

@Composable
private fun AdjustRow(label: String, value: Float, onChange: (Float) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = ShoyoColors.TextPrimary, fontWeight = FontWeight.Bold, modifier = Modifier.width(92.dp))
        NeonSlider(value, { onChange(it.roundToInt().toFloat()) }, -100f..100f, Modifier.weight(1f).padding(horizontal = 8.dp))
        Text("${value.roundToInt()}%", color = ShoyoColors.Accent, fontWeight = FontWeight.Bold, modifier = Modifier.width(48.dp))
    }
}

@Composable
private fun HueRow(label: String, argb: Int, onChange: (Int) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = ShoyoColors.TextPrimary, fontWeight = FontWeight.Bold, modifier = Modifier.width(60.dp))
        NeonSlider(hueOf(argb), { onChange(hueColor(it).toArgb()) }, 0f..359f, Modifier.weight(1f).padding(horizontal = 8.dp))
        Box(Modifier.size(28.dp).clip(ParaShape(7.dp)).background(Color(argb)))
    }
}

/** Fundo aurora da prévia (usa as cores da paleta de prévia, não do tema atual). */
private fun Modifier.drawBackgroundAurora(p: ShoyoPalette): Modifier = drawBehind {
    drawRect(Brush.radialGradient(listOf(p.accent.copy(alpha = 0.30f), Color.Transparent), center = Offset(size.width * 0.3f, 0f), radius = size.width * 0.6f))
    drawRect(Brush.radialGradient(listOf(p.accentDeep.copy(alpha = 0.22f), Color.Transparent), center = Offset(size.width * 0.8f, size.height * 0.3f), radius = size.width * 0.5f))
}
