package com.monka.events;

import com.monka.splashzone.Splashzone;
import com.monka.splashzone.client.model.UggEntityModel;
import com.monka.splashzone.client.renderer.UggEntityRenderer;
import com.monka.splashzone.registry.EntityRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber(modid = Splashzone.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.UGG_ENTITY.get(), UggEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(UggEntityModel.LAYER_LOCATION, UggEntityModel::createBodyLayer);
    }
}