package com.shoyoomc.cyberneticlauncher.game.account.wardrobe

import com.shoyoomc.cyberneticlauncher.petlib.ArmModel

/**
 * 皮肤模型类型到 PetLib 臂型的映射
 */
fun SkinModelType.toArmModel(): ArmModel? = when (this) {
    SkinModelType.STEVE -> ArmModel.Classic
    SkinModelType.ALEX -> ArmModel.Slim
    SkinModelType.NONE -> null
}