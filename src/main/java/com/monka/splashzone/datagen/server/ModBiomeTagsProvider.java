package com.monka.splashzone.datagen.server;

import com.monka.splashzone.Splashzone;
import com.monka.splashzone.registry.TagRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends TagsProvider<Biome> {
    public ModBiomeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.BIOME, lookupProvider, Splashzone.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(TagRegistry.SPAWNS_UGGS).addTags(BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA, BiomeTags.IS_BADLANDS, Tags.Biomes.IS_SWAMP, BiomeTags.HAS_JUNGLE_TEMPLE);
    }
}
