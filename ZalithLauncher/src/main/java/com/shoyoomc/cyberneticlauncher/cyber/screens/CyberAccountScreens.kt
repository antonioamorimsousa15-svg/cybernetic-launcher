/*
 * CYBERNETIC LAUNCHER
 * Derivado do Zalith Launcher 2 (GPLv3). Copyright (C) 2025-2026 MovTery <movtery228@qq.com> and contributors.
 * Este programa é software livre: você pode redistribuí-lo e/ou modificá-lo sob os termos da
 * GNU General Public License v3 (ou posterior). Veja <https://www.gnu.org/licenses/gpl-3.0.txt>.
 */
package com.shoyoomc.cyberneticlauncher.cyber.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.shoyoomc.cyberneticlauncher.cyber.theme.BadgeKind
import com.shoyoomc.cyberneticlauncher.cyber.theme.DiamondRow
import com.shoyoomc.cyberneticlauncher.cyber.theme.GhostButton
import com.shoyoomc.cyberneticlauncher.cyber.theme.NeonButton
import com.shoyoomc.cyberneticlauncher.cyber.theme.SelectChip
import com.shoyoomc.cyberneticlauncher.cyber.theme.ShoyoColors
import com.shoyoomc.cyberneticlauncher.cyber.theme.StatusBadge
import com.shoyoomc.cyberneticlauncher.cyber.theme.cornerBrackets
import com.shoyoomc.cyberneticlauncher.cyber.theme.tap
import com.shoyoomc.cyberneticlauncher.game.account.Account
import com.shoyoomc.cyberneticlauncher.game.account.AccountType
import com.shoyoomc.cyberneticlauncher.game.account.getAccountTypeName
import com.shoyoomc.cyberneticlauncher.game.account.isLocalAccount
import com.shoyoomc.cyberneticlauncher.game.account.isMicrosoftAccount
import com.shoyoomc.cyberneticlauncher.game.account.isSkinChangeAllowed
import com.shoyoomc.cyberneticlauncher.game.account.wardrobe.EmptyCape
import com.shoyoomc.cyberneticlauncher.game.account.wardrobe.SkinModelType
import com.shoyoomc.cyberneticlauncher.game.account.wardrobe.toArmModel
import com.shoyoomc.cyberneticlauncher.game.account.yggdrasil.PlayerProfile
import com.shoyoomc.cyberneticlauncher.game.account.yggdrasil.findUsing
import com.shoyoomc.cyberneticlauncher.path.PathManager
import com.shoyoomc.cyberneticlauncher.petlib.ArmModel
import com.shoyoomc.cyberneticlauncher.petlib.PetAnimation
import com.shoyoomc.cyberneticlauncher.ui.components.PetModelView
import com.shoyoomc.cyberneticlauncher.ui.screens.content.elements.ChangeCape
import com.shoyoomc.cyberneticlauncher.ui.screens.content.elements.ChangeSkin
import com.shoyoomc.cyberneticlauncher.ui.screens.content.elements.ChangeSkin.SkinModel
import com.shoyoomc.cyberneticlauncher.ui.screens.content.elements.PlayerFace
import com.shoyoomc.cyberneticlauncher.ui.screens.content.elements.SelectCapeDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

// ---------------------------------------------------------------------------------------------
// Item da lista de contas (losango, selo ATIVA e ações em ícones)
// ---------------------------------------------------------------------------------------------
@Composable
private fun CyberIconAction(icon: ImageVector, enabled: Boolean = true, danger: Boolean = false, onClick: () -> Unit) {
    val c = if (danger) ShoyoColors.Accent else ShoyoColors.TextPrimary
    Box(
        Modifier.size(40.dp).clip(RoundedCornerShape(50))
            .border(1.dp, ShoyoColors.PanelBorder, RoundedCornerShape(50))
            .alpha(if (enabled) 1f else 0.35f)
            .then(if (enabled) Modifier.tap(onClick) else Modifier),
        contentAlignment = Alignment.Center
    ) { Icon(icon, null, tint = c, modifier = Modifier.size(20.dp)) }
}

@Composable
fun CyberAccountItem(
    currentAccount: Account?,
    account: Account,
    onSelected: (Account) -> Unit,
    onWardrobe: () -> Unit,
    onRefresh: () -> Unit,
    onCopyUUID: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selected = currentAccount?.uniqueUUID == account.uniqueUUID
    DiamondRow(
        modifier = modifier,
        onClick = { if (!selected) onSelected(account) },
        trailing = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                if (selected) StatusBadge("Ativa", BadgeKind.Selected)
                CyberIconAction(Icons.Filled.Checkroom, enabled = account.isSkinChangeAllowed(), onClick = onWardrobe)
                CyberIconAction(Icons.Filled.Refresh, enabled = account.accountType != AccountType.LOCAL.tag, onClick = onRefresh)
                CyberIconAction(Icons.Filled.ContentCopy, onClick = onCopyUUID)
                CyberIconAction(Icons.Filled.Delete, danger = true, onClick = onDelete)
            }
        }
    ) {
        PlayerFace(account = account, avatarSize = 44.dp)
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(account.username, color = ShoyoColors.TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(getAccountTypeName(account), color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.bodySmall)
        }
    }
}

// ---------------------------------------------------------------------------------------------
// Guarda-roupa: pré-visualização + skins + estilo de braço + mais opções
// ---------------------------------------------------------------------------------------------
@Composable
private fun WardrobeSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    val shape = CutCornerShape(topStart = 12.dp, bottomEnd = 12.dp)
    Column(
        Modifier.fillMaxWidth().clip(shape).background(ShoyoColors.RowBg)
            .border(1.dp, ShoyoColors.PanelBorder, shape).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(title, color = ShoyoColors.Accent, style = MaterialTheme.typography.labelSmall, fontSize = 13.sp)
        content()
    }
}

@Composable
private fun SkinTile(
    label: String,
    highlighted: Boolean,
    onRemove: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(14.dp)
    Box(Modifier.size(78.dp)) {
        Box(
            Modifier.fillMaxSize().clip(shape).background(ShoyoColors.Glass)
                .border(if (highlighted) 2.dp else 1.dp, if (highlighted) ShoyoColors.Accent else ShoyoColors.PanelBorder, shape)
                .then(if (onClick != null) Modifier.tap(onClick) else Modifier),
            contentAlignment = Alignment.Center
        ) {
            content()
            Text(
                label, color = ShoyoColors.TextPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 3.dp)
            )
        }
        if (onRemove != null) {
            Box(
                Modifier.align(Alignment.TopEnd).size(22.dp).clip(RoundedCornerShape(50))
                    .background(ShoyoColors.Accent).tap(onRemove),
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Filled.Close, null, tint = ShoyoColors.OnAccent, modifier = Modifier.size(14.dp)) }
        }
    }
}

@Composable
private fun OptionRow(icon: ImageVector, text: String, enabled: Boolean = true, onClick: () -> Unit) {
    val shape = RoundedCornerShape(14.dp)
    Row(
        Modifier.fillMaxWidth().clip(shape).background(ShoyoColors.Glass)
            .alpha(if (enabled) 1f else 0.4f)
            .then(if (enabled) Modifier.tap(onClick) else Modifier)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = ShoyoColors.Accent, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(12.dp))
        Text(text, color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun CyberWardrobeDialog(
    account: Account,
    availableCapes: List<PlayerProfile.Cape>? = null,
    skinState: ChangeSkin,
    onSkinStateChange: (ChangeSkin) -> Unit,
    capeState: ChangeCape,
    onCapeStateChange: (ChangeCape) -> Unit,
    isImportingSkin: Boolean,
    onSkinPicked: (Uri) -> Unit,
    onDismissRequest: () -> Unit,
    onResetSkin: () -> Unit,
    onApplySkin: (File, SkinModelType) -> Unit,
    onApplyCape: (PlayerProfile.Cape) -> Unit,
    onFetchCapes: () -> Unit
) {
    var showCapeSelector by remember { mutableStateOf(false) }
    var isFetchingCapes by remember { mutableStateOf(false) }
    var currentCapeToLoad by remember { mutableStateOf(EmptyCape) }
    var currentUsingCape by remember { mutableStateOf(EmptyCape) }

    LaunchedEffect(availableCapes) {
        if (account.isMicrosoftAccount()) {
            if (availableCapes != null) {
                isFetchingCapes = false
                val using = availableCapes.findUsing() ?: EmptyCape
                currentUsingCape = using
                currentCapeToLoad = using
            } else {
                isFetchingCapes = true
                onFetchCapes()
            }
        }
    }

    val previewCape by produceState<ByteArray?>(null, currentCapeToLoad, availableCapes) {
        if (!account.isMicrosoftAccount()) return@produceState
        value = withContext(Dispatchers.IO) {
            if (currentCapeToLoad != EmptyCape) {
                runCatching {
                    currentCapeToLoad.getFile(PathManager.DIR_ACCOUNT_CAPE).inputStream().use { it.readBytes() }
                }.getOrNull()
            } else if (availableCapes == null && account.getCapeFile().exists()) {
                runCatching { account.getCapeFile().inputStream().use { it.readBytes() } }.getOrNull()
            } else null
        }
    }

    val skinPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let(onSkinPicked)
    }

    val previewSkin by produceState<ByteArray?>(null, skinState) {
        value = withContext(Dispatchers.IO) {
            when (skinState) {
                ChangeSkin.None -> account.getSkinFile().takeIf { it.exists() }?.inputStream()?.use { it.readBytes() }
                is ChangeSkin.ChangeSkinData -> runCatching { skinState.cacheFile.inputStream().use { it.readBytes() } }.getOrNull()
                ChangeSkin.ResetSkin -> null
            }
        }
    }
    val previewModel: SkinModelType = when (skinState) {
        ChangeSkin.None -> account.skinModelType
        is ChangeSkin.ChangeSkinData -> skinState.data.getModelType()
        ChangeSkin.ResetSkin -> SkinModelType.STEVE
    }
    val previewArm = when (skinState) {
        ChangeSkin.None -> account.skinModelType.toArmModel()
        is ChangeSkin.ChangeSkinData -> skinState.data.getModelType().toArmModel()
        ChangeSkin.ResetSkin -> ArmModel.Classic
    }
    val modelLabel = if (previewModel == SkinModelType.ALEX) "SLIM" else "WIDE"
    val canApply = skinState != ChangeSkin.None || capeState != ChangeCape.None

    Dialog(onDismissRequest = onDismissRequest, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        val shape = CutCornerShape(topStart = 28.dp, bottomEnd = 28.dp)
        Row(
            Modifier.fillMaxWidth(0.94f).heightIn(max = 420.dp).clip(shape)
                .background(Brush.verticalGradient(listOf(ShoyoColors.BgAlt, ShoyoColors.Bg)))
                .border(1.5.dp, Brush.linearGradient(listOf(ShoyoColors.Secondary, ShoyoColors.AccentDeep)), shape)
                .cornerBrackets(ShoyoColors.Secondary)
                .padding(18.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // ---- pré-visualização ----
            Column(
                Modifier.weight(0.9f).fillMaxHeight()
                    .clip(RoundedCornerShape(18.dp)).background(ShoyoColors.Glass)
                    .border(1.dp, ShoyoColors.PanelBorder, RoundedCornerShape(18.dp))
                    .padding(12.dp)
            ) {
                Text(
                    "PRÉ-VISUALIZAÇÃO", color = ShoyoColors.Accent, style = MaterialTheme.typography.labelSmall,
                    fontSize = 13.sp,
                    modifier = Modifier.clip(RoundedCornerShape(50)).border(1.dp, ShoyoColors.PanelBorder, RoundedCornerShape(50))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
                PetModelView(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    skin = previewSkin,
                    cape = previewCape,
                    arm = previewArm,
                    animation = PetAnimation.Walking,
                    animationSpeed = 0.8f,
                    gazeEnabled = false
                )
            }

            // ---- painel direito ----
            Column(Modifier.weight(1.3f).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    PlayerFace(account = account, avatarSize = 40.dp)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("Guarda-roupa", color = ShoyoColors.TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(account.username, color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Column(
                    Modifier.weight(1f).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // skins
                    WardrobeSection("SKINS") {
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            SkinTile(label = if (isImportingSkin) "..." else "Nova", highlighted = false, onClick = {
                                if (!isImportingSkin) skinPicker.launch(arrayOf("image/png"))
                            }) { Icon(Icons.Filled.Add, null, tint = ShoyoColors.Accent, modifier = Modifier.size(30.dp)) }

                            when (skinState) {
                                is ChangeSkin.ChangeSkinData -> SkinTile(
                                    label = "NOVA · $modelLabel", highlighted = true,
                                    onRemove = { onSkinStateChange(ChangeSkin.None) }
                                ) { PlayerFace(account = account, avatarSize = 44.dp) }
                                ChangeSkin.ResetSkin -> {}
                                ChangeSkin.None -> SkinTile(
                                    label = modelLabel, highlighted = true,
                                    onRemove = if (account.isLocalAccount() && account.hasSkinFile)
                                        ({ onSkinStateChange(ChangeSkin.ResetSkin) }) else null
                                ) { PlayerFace(account = account, avatarSize = 44.dp) }
                            }
                        }
                    }

                    // estilo de braço
                    WardrobeSection("ESTILO DE BRAÇO") {
                        val pending = skinState as? ChangeSkin.ChangeSkinData
                        val editable = pending != null && pending.data !is SkinModel.Rejected
                        val current = pending?.data?.getModelTypeNullable() ?: previewModel
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(Modifier.weight(1f).alpha(if (editable) 1f else 0.5f)) {
                                SelectChip("Braços Masculino", current == SkinModelType.STEVE) {
                                    if (editable) onSkinStateChange(pending!!.copy(data = SkinModel.Model(SkinModelType.STEVE)))
                                }
                            }
                            Box(Modifier.weight(1f).alpha(if (editable) 1f else 0.5f)) {
                                SelectChip("Braços Feminino", current == SkinModelType.ALEX) {
                                    if (editable) onSkinStateChange(pending!!.copy(data = SkinModel.Model(SkinModelType.ALEX)))
                                }
                            }
                        }
                    }

                    // mais opções
                    WardrobeSection("MAIS OPÇÕES") {
                        if (account.isMicrosoftAccount()) {
                            OptionRow(
                                Icons.Filled.Palette,
                                if (isFetchingCapes) "Buscando capas..." else "Capa",
                                enabled = !isFetchingCapes
                            ) { showCapeSelector = true }
                        }
                        OptionRow(
                            Icons.Filled.RestartAlt, "Resetar",
                            enabled = account.isLocalAccount() && account.hasSkinFile && skinState != ChangeSkin.ResetSkin
                        ) { onSkinStateChange(ChangeSkin.ResetSkin) }
                    }
                }

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    GhostButton("Cancelar", null, Modifier.weight(1f), onDismissRequest)
                    NeonButton(
                        "Aplicar", Icons.Filled.Check,
                        Modifier.weight(1f).alpha(if (canApply) 1f else 0.4f)
                    ) {
                        if (!canApply) return@NeonButton
                        when (skinState) {
                            is ChangeSkin.ChangeSkinData -> onApplySkin(skinState.cacheFile, skinState.data.getModelType())
                            ChangeSkin.ResetSkin -> onResetSkin()
                            ChangeSkin.None -> {}
                        }
                        if (capeState is ChangeCape.ChangeCapeData) onApplyCape(capeState.cape)
                        onDismissRequest()
                    }
                }
            }
        }
    }

    if (showCapeSelector) {
        val cape = if (capeState is ChangeCape.ChangeCapeData) capeState.cape else currentUsingCape
        SelectCapeDialog(
            capes = buildList { add(EmptyCape); addAll(availableCapes ?: emptyList()) },
            selectedCape = cape,
            onSelected = { picked ->
                onCapeStateChange(if (picked != currentUsingCape) ChangeCape.ChangeCapeData(picked) else ChangeCape.None)
                currentCapeToLoad = picked
                showCapeSelector = false
            },
            onDismiss = { showCapeSelector = false }
        )
    }
}
