package com.chiikawa;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class kawaModel extends GeoModel<ChiikawEntity> {
    @Override
    public ResourceLocation getModelResource(ChiikawEntity chiikawEntity) {
        return new ResourceLocation(chiikawamod.MODID,"geo/kawa_entity.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(ChiikawEntity chiikawEntity) {
        return new ResourceLocation(chiikawamod.MODID,"textures/entity/kawa_entity.png");
    }
    @Override
    public ResourceLocation getAnimationResource(ChiikawEntity chiikawEntity) {
        return new ResourceLocation(chiikawamod.MODID,"animations/model.animation.json");
    }
}
