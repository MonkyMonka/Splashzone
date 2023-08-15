package com.monka.splashzone.client.renderer;

import com.monka.splashzone.Splashzone;
import com.monka.splashzone.client.model.UggEggEntityModel;
import com.monka.splashzone.client.model.layer.UggEggEntityLayer;
import com.monka.splashzone.entity.UggEggEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class UggEggEntityRenderer extends MobRenderer<UggEggEntity, UggEggEntityModel<UggEggEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Splashzone.MODID, "textures/entity/ugg/ugg_egg_placed.png");

    public UggEggEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new UggEggEntityModel<>(ctx.bakeLayer(UggEggEntityModel.UGG_EGG_ENTITY)), 0.5f);
        this.addLayer(new UggEggEntityLayer<>(this, ctx.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(UggEggEntity entity) {
        return TEXTURE;
    }

    @Nullable
    @Override
    protected RenderType getRenderType(UggEggEntity pEntity, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
        if (p_115324_) {
            return RenderType.itemEntityTranslucentCull(TEXTURE);
        } else if (p_115323_) {
            return this.model.renderType(TEXTURE);
        } else {
            return p_115325_ ? RenderType.outline(TEXTURE) : null;
        }
    }
}