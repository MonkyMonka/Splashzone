package com.monka.splashzone.datagen;

import com.monka.splashzone.Splashzone;
import com.monka.splashzone.datagen.client.ModBlockStateProvider;
import com.monka.splashzone.datagen.client.ModItemModelProvider;
import com.monka.splashzone.datagen.client.lang.ModEnUsProvider;
import com.monka.splashzone.datagen.server.ModBiomeTagsProvider;
import com.monka.splashzone.datagen.server.ModBlockTagsProvider;
import com.monka.splashzone.datagen.server.ModItemTagsProvider;
import com.monka.splashzone.datagen.server.ModRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Splashzone.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        var lookup = event.getLookupProvider();

        if (event.includeClient()) {

            // Client Data Generation
            generator.addProvider(true, new ModBlockStateProvider(packOutput, existingFileHelper));
            generator.addProvider(true, new ModItemModelProvider(packOutput, existingFileHelper));
            generator.addProvider(true, new ModEnUsProvider(packOutput));
        }

        if (event.includeServer()) {

            ModBlockTagsProvider blockTags = new ModBlockTagsProvider(packOutput, lookup, existingFileHelper);

            // Server Data Generation
            generator.addProvider(true, new ModRecipeProvider(packOutput));
            generator.addProvider(true, blockTags);
            generator.addProvider(true, new ModItemTagsProvider(packOutput, lookup, blockTags, existingFileHelper));
            generator.addProvider(true, new ModBiomeTagsProvider(packOutput, lookup, existingFileHelper));
            generator.addProvider(true, ModLootTableProvider.create(packOutput));

        }

    }
}