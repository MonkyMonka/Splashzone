package com.monka.splashzone.datagen.server;

import com.monka.splashzone.registry.EntityRegistry;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Stream;

public class ModEntityLootTables extends EntityLootSubProvider {

    public ModEntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
//        this.add(EntityRegistry.UGG_EGG_ENTITY.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool()
//                        .setRolls(ConstantValue.exactly(1))
//                        .add(LootItem.lootTableItem(ItemRegistry.UGG_EGG.get())
//                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
//                        )
//                )
//        );
        this.add(EntityRegistry.UGG_EGG_ENTITY.get(), LootTable.lootTable());
        this.add(EntityRegistry.UGG_ENTITY.get(), LootTable.lootTable());
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return EntityRegistry.ENTITIES.getEntries().stream().map(RegistryObject::get);
    }
}