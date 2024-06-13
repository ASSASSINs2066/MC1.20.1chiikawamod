package com.chiikawa;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ChomperRenderer extends GeoEntityRenderer<ChiikawEntity> {
    public ChomperRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ChiikawaModel());
        this.shadowRadius = 0.3f;
    }
    @Override
    public ResourceLocation getTextureLocation(ChiikawEntity instance) {
        return new ResourceLocation(chiikawamod.MODID, "textures/entity/texture.png");
    }

}
