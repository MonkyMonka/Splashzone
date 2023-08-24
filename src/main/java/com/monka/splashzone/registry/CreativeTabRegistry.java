package com.monka.splashzone.registry;

import com.monka.splashzone.Splashzone;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Splashzone.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CreativeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Splashzone.MODID);

    public static final List<Supplier<? extends ItemLike>> SPLASHZONE_TAB_ITEMS = new ArrayList<>();

    public static final RegistryObject<CreativeModeTab> SPLASHZONE_TAB = TABS.register("splashzone_tab", () -> CreativeModeTab.builder()
            // Set name of tab to display
            .title(Component.translatable("item_group." + Splashzone.MODID))
            // Set icon of creative tab
            .icon(() -> new ItemStack(ItemRegistry.UGG_EGG.get()))
            // Add default items to tab
            .displayItems((displayParams, output) ->
                    SPLASHZONE_TAB_ITEMS.forEach(itemLike -> output.accept(itemLike.get())))
            .withSearchBar()
            .build()
    );

    public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> itemLike) {
        SPLASHZONE_TAB_ITEMS.add(itemLike);
        return itemLike;
    }

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ItemRegistry.UGG_SPAWN_EGG.get());
        }

        if (event.getTab() == SPLASHZONE_TAB.get()) {
        }
    }
}
