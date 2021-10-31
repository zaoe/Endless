package com.yuo.endless.Tiles;

import com.yuo.endless.Blocks.BlockRegistry;
import com.yuo.endless.Endless;
import com.yuo.endless.Recipe.EndlessRecipeType;
import com.yuo.endless.Recipe.RecipeTypeRegistry;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//方块实体类型注册
public class TileTypeRegistry {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Endless.MODID);

    public static final RegistryObject<TileEntityType<ExtremeCraftTile>> EXTREME_CRAFT_TILE = TILE_ENTITIES.register("extreme_craft_tile",
            () -> TileEntityType.Builder.create(ExtremeCraftTile::new, BlockRegistry.extremeCraftingTable.get()).build(null));
    public static final RegistryObject<TileEntityType<NeutronCollectorTile>> NEUTRON_COLLECTOR_TILE = TILE_ENTITIES.register("neutron_collector_tile",
            () -> TileEntityType.Builder.create(NeutronCollectorTile::new, BlockRegistry.neutronCollector.get()).build(null));

}
