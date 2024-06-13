package com.chiikawa;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class KawaRenderer extends GeoEntityRenderer<KawaEntity> {
    public KawaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new WsqModel());
        this.shadowRadius = 0.3f;
    }
    @Override
    public ResourceLocation getTextureLocation(KawaEntity instance) {
        return new ResourceLocation(chiikawamod.MODID, "textures/entity/wsq_entity.png");
    }
}
