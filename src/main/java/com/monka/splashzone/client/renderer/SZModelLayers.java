package com.monka.splashzone.client.renderer;

import com.monka.splashzone.Splashzone;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class SZModelLayers {

    public static final ModelLayerLocation UGG_EGG_LAYER = register("ugg_egg_entity", "outer");

    private static ModelLayerLocation register(String name) {
        return new ModelLayerLocation(new ResourceLocation(Splashzone.MODID, name), "main");
    }

    private static ModelLayerLocation register(String name, String layerName) {
        return new ModelLayerLocation(new ResourceLocation(Splashzone.MODID, name), layerName);
    }
}
