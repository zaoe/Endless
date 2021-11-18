package com.yuo.endless.Blocks;

import com.yuo.endless.Endless;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//方块注册
public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Endless.MODID);

    public static RegistryObject<Block> infinityBlock = BLOCKS.register("infinity_block", () -> {
        return new OrdinaryBlock(Material.IRON, 99, ToolType.PICKAXE, 1000, 1000);
    });
    public static RegistryObject<Block> crystalMatrix = BLOCKS.register("crystal_matrix", () -> {
        return new OrdinaryBlock(Material.IRON, 4, ToolType.PICKAXE, 10, 50);
    });
    public static RegistryObject<Block> neutroniumBlock = BLOCKS.register("neutronium_block", () -> {
        return new OrdinaryBlock(Material.IRON, 5, ToolType.PICKAXE, 15, 100);
    });
    public static RegistryObject<Block> tripleCraft = BLOCKS.register("triple_craft", () -> {
        return new OrdinaryBlock(Material.WOOD, 0, ToolType.AXE, 5, 5);
    });
    public static RegistryObject<Block> doubleCraft = BLOCKS.register("double_craft", () -> {
        return new OrdinaryBlock(Material.WOOD, 1, ToolType.AXE, 5, 5);
    });
    public static RegistryObject<Block> extremeCraftingTable = BLOCKS.register("extreme_crafting_table", ExtremeCraft::new);
    public static RegistryObject<Block> neutronCollector = BLOCKS.register("neutron_collector", NeutronCollector::new);
    public static RegistryObject<Block> neutroniumCompressor = BLOCKS.register("neutronium_compressor", NeutroniumCompressor::new);
}
