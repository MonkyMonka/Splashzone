package com.monka.splashzone.client.renderer;

import com.monka.splashzone.Splashzone;
import com.monka.splashzone.client.model.UggEggEntityModel;
import com.monka.splashzone.entity.UggEggEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
public class UggEggEntityRenderer extends MobRenderer<UggEggEntity, UggEggEntityModel<UggEggEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Splashzone.MODID, "textures/entity/ugg/ugg_egg_placed.png");

    public UggEggEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new UggEggEntityModel<>(ctx.bakeLayer(UggEggEntityModel.LAYER_LOCATION)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(UggEggEntity entity) {
        return TEXTURE;
    }
}