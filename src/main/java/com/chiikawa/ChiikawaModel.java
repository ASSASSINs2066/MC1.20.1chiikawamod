package com.chiikawa;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ChiikawaModel extends GeoModel<ChiikawEntity> {
    @Override
    public ResourceLocation getModelResource(ChiikawEntity chiikawEntity) {
        return new ResourceLocation(chiikawamod.MODID,"geo/chiikawaentity.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(ChiikawEntity chiikawEntity) {
        return new ResourceLocation(chiikawamod.MODID,"textures/entity/texture.png");
    }
    @Override
    public ResourceLocation getAnimationResource(ChiikawEntity chiikawEntity) {
        return new ResourceLocation(chiikawamod.MODID,"animations/model.animation.json");
    }
}
