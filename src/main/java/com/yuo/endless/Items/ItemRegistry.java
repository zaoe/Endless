package com.yuo.endless.Items;

import com.yuo.endless.Armor.InfinityArmor;
import com.yuo.endless.Blocks.BlockRegistry;
import com.yuo.endless.Endless;
import com.yuo.endless.Items.Tool.*;
import com.yuo.endless.tab.ModGroup;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//物品注册管理器
public class ItemRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Endless.MODID);

	//物品
	public static RegistryObject<Item> meatballs = ITEMS.register("meatballs", () -> {
		return new OrdinaryFood(MyFoods.MEAT_BALLS);
	});
	public static RegistryObject<Item> stew = ITEMS.register("stew", () -> {
		return new OrdinaryFood(MyFoods.STEW);
	});
	public static RegistryObject<Item> diamondLattice = ITEMS.register("diamond_lattice", OrdinaryItem::new);
	public static RegistryObject<Item> crystalMatrixIngot = ITEMS.register("crystal_matrix_ingot", OrdinaryItem::new);
	public static RegistryObject<Item> neutronPile = ITEMS.register("neutron_pile", OrdinaryItem::new);
	public static RegistryObject<Item> neutronNugget = ITEMS.register("neutron_nugget", OrdinaryItem::new);
	public static RegistryObject<Item> neutroniumIngot = ITEMS.register("neutronium_ingot", OrdinaryItem::new);
	public static RegistryObject<Item> endestPearl = ITEMS.register("endestpearl", OrdinaryItem::new);
	public static RegistryObject<Item> infinityCatalyst = ITEMS.register("infinity_catalyst", OrdinaryItem::new);
	public static RegistryObject<Item> infinityIngot = ITEMS.register("infinity_ingot", OrdinaryItem::new);
	public static RegistryObject<Item> recordFragment = ITEMS.register("record_fragment", OrdinaryItem::new);
	public static RegistryObject<Item> matterCluster = ITEMS.register("mattercluster", OrdinaryItem::new);

	//工具
	public static RegistryObject<Item> infinityPickaxe = ITEMS.register("infinity_pickaxe", InfinityPickaxe::new);
	public static RegistryObject<Item> infinityAxe = ITEMS.register("infinity_axe", InfinityAxe::new);
	public static RegistryObject<Item> infinityShovel = ITEMS.register("infinity_shovel", InfinityShovel::new);
	public static RegistryObject<Item> infinityHoe = ITEMS.register("infinity_hoe", InfinityHoe::new);
	public static RegistryObject<Item> infinitySword = ITEMS.register("infinity_sword", InfinitySword::new);
	public static RegistryObject<Item> skullfireSword = ITEMS.register("skullfire_sword", SkullfireSword::new);
	public static RegistryObject<Item> infinityBow = ITEMS.register("infinity_bow", InfinityBow::new);

	//盔甲
	public static RegistryObject<ArmorItem> infinityHead = ITEMS.register("infinity_head", () -> {
		return new InfinityArmor(EquipmentSlotType.HEAD);
	});
	public static RegistryObject<ArmorItem> infinityChest = ITEMS.register("infinity_chest", () -> {
		return new InfinityArmor(EquipmentSlotType.CHEST);
	});
	public static RegistryObject<ArmorItem> infinityLegs = ITEMS.register("infinity_legs", () -> {
		return new InfinityArmor(EquipmentSlotType.LEGS);
	});
	public static RegistryObject<ArmorItem> infinityFeet = ITEMS.register("infinity_feet", () -> {
		return new InfinityArmor(EquipmentSlotType.FEET);
	});

	//注册方块物品
	public static RegistryObject<BlockItem> blockInfinity = ITEMS.register("infinity_block", () ->{
		return new BlockItem(BlockRegistry.infinityBlock.get(), new Item.Properties().group(ModGroup.myGroup));
	});
	public static RegistryObject<BlockItem> crystalMatrix = ITEMS.register("crystal_matrix", () ->{
		return new BlockItem(BlockRegistry.crystalMatrix.get(), new Item.Properties().group(ModGroup.myGroup));
	});
	public static RegistryObject<BlockItem> neutroniumBlock = ITEMS.register("neutronium_block", () ->{
		return new BlockItem(BlockRegistry.neutroniumBlock.get(), new Item.Properties().group(ModGroup.myGroup));
	});
	public static RegistryObject<BlockItem> triple_craft = ITEMS.register("triple_craft", () ->{
		return new BlockItem(BlockRegistry.tripleCraft.get(), new Item.Properties().group(ModGroup.myGroup));
	});
	public static RegistryObject<BlockItem> double_craft = ITEMS.register("double_craft", () ->{
		return new BlockItem(BlockRegistry.doubleCraft.get(), new Item.Properties().group(ModGroup.myGroup));
	});
	public static RegistryObject<BlockItem> extremeCraftingTable = ITEMS.register("extreme_crafting_table", () ->{
		return new BlockItem(BlockRegistry.extremeCraftingTable.get(), new Item.Properties().group(ModGroup.myGroup));
	});
	public static RegistryObject<BlockItem> neutronCollector = ITEMS.register("neutron_collector", () ->{
		return new BlockItem(BlockRegistry.neutronCollector.get(), new Item.Properties().group(ModGroup.myGroup));
	});
	public static RegistryObject<BlockItem> neutroniumCompressor = ITEMS.register("neutronium_compressor", () ->{
		return new BlockItem(BlockRegistry.neutroniumCompressor.get(), new Item.Properties().group(ModGroup.myGroup));
	});
}
