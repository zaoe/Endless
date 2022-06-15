package com.yuo.endless.Blocks;

import com.yuo.endless.Tiles.CompressorChestTile;
import com.yuo.endless.Tiles.TileTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class CompressorChest extends AbsEndlessChest{

    public CompressorChest() {
        super(TileTypeRegistry.COMPRESS_CHEST_TILE::get, EndlessChestType.COMPRESSOR,
                Properties.create(Material.WOOD).hardnessAndResistance(4f, 10f).sound(SoundType.WOOD));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CompressorChestTile();
    }
}
