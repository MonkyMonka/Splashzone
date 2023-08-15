package com.monka.splashzone.client.renderer;

import com.google.common.collect.Maps;
import com.monka.splashzone.Splashzone;
import com.monka.splashzone.client.model.UggEntityModel;
import com.monka.splashzone.entity.UggEntity;
import com.monka.splashzone.entity.variant.UggVariant;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class UggEntityRenderer extends MobRenderer<UggEntity, UggEntityModel<UggEntity>> {
    public static final Map<UggVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(UggVariant.class), (p_114874_) -> {
                p_114874_.put(UggVariant.BANANA,
                        new ResourceLocation(Splashzone.MODID, "textures/entity/ugg/ugg_banana.png"));
                p_114874_.put(UggVariant.CHARCOAL,
                        new ResourceLocation(Splashzone.MODID, "textures/entity/ugg/ugg_charcoal.png"));
                p_114874_.put(UggVariant.LICHEN,
                        new ResourceLocation(Splashzone.MODID, "textures/entity/ugg/ugg_lichen.png"));
                p_114874_.put(UggVariant.RUDDY,
                        new ResourceLocation(Splashzone.MODID, "textures/entity/ugg/ugg_ruddy.png"));
                p_114874_.put(UggVariant.UMBER,
                        new ResourceLocation(Splashzone.MODID, "textures/entity/ugg/ugg_umber.png"));
            });

    public UggEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new UggEntityModel<>(ctx.bakeLayer(UggEntityModel.UGG)), 0.3f);
    }
    @Override
    public ResourceLocation getTextureLocation(UggEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }
}
