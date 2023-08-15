package com.monka.splashzone.client.renderer;

import com.monka.splashzone.Splashzone;
import com.monka.splashzone.client.model.UggEntityModel;
import com.monka.splashzone.entity.UggEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class UggEntityRenderer extends MobRenderer<UggEntity, UggEntityModel<UggEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Splashzone.MODID, "textures/entity/ugg/ugg_banana.png");

    public UggEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new UggEntityModel<>(ctx.bakeLayer(UggEntityModel.LAYER_LOCATION)), 1.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(UggEntity entity) {
        return TEXTURE;
    }
}
