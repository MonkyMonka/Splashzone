package com.monka.splashzone.datagen.server;

import com.monka.splashzone.Splashzone;
import com.monka.splashzone.block.BlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModBlockTags extends BlockTagsProvider {
    public ModBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Splashzone.MODID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.registerModTags();
        this.registerMinecraftTags();
        this.registerForgeTags();
        this.registerCompatibilityTags();

        this.registerBlockMineables();
    }

    protected void registerBlockMineables() {
    }

    protected void registerModTags() {
    }

    protected void registerMinecraftTags() {
    }

    protected void registerForgeTags() {
    }
    protected void registerCompatibilityTags() {
    }
}
