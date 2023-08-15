package com.monka.splashzone.datagen.client.lang;

import com.monka.splashzone.Splashzone;
import com.monka.splashzone.registry.EntityRegistry;
import com.monka.splashzone.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnUsProvider extends LanguageProvider {

    public ModEnUsProvider(PackOutput gen) {
        super(gen, Splashzone.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Items
        add(ItemRegistry.UGG_EGG.get(), "Ugg Egg");
        add(ItemRegistry.UGG_SPAWN_EGG.get(), "Ugg Spawn Egg");
        // Blocks

        // Entities
        add(EntityRegistry.UGG_ENTITY.get(), "Ugg");
        add(EntityRegistry.UGG_EGG_ENTITY.get(), "Ugg Egg");
    }

}