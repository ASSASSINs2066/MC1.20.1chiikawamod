package com.chiikawa;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WsqModel extends GeoModel<KawaEntity> {
    @Override
    public ResourceLocation getModelResource(KawaEntity chiikawEntity) {
        return new ResourceLocation(chiikawamod.MODID,"geo/wsq_entity.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(KawaEntity chiikawEntity) {
        return new ResourceLocation(chiikawamod.MODID,"textures/entity/wsq_entity.png");
    }
    @Override
    public ResourceLocation getAnimationResource(KawaEntity chiikawEntity) {
        return new ResourceLocation(chiikawamod.MODID,"animations/model.animation.json");
    }
}
