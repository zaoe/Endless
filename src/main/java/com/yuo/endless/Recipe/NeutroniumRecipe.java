package com.yuo.endless.Recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class NeutroniumRecipe implements IRecipe<IInventory> {

    private final ItemStack input;
    private final int count; //数量 可能大于64
    private final ItemStack output;
    private final ResourceLocation id;

    public NeutroniumRecipe(ResourceLocation idIn, ItemStack inputIn, int countIn, ItemStack outputIn){
        this.id = idIn;
        this.input = inputIn;
        this.count = countIn;
        this.output = outputIn;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<NeutroniumRecipe>{

        @Override
        public NeutroniumRecipe read(ResourceLocation recipeId, JsonObject json) { //从json中获取信息
            ItemStack input = deserializeItem(JSONUtils.getJsonObject(json, "input"));
            int count = JSONUtils.getInt(json, "count");
            ItemStack output = deserializeItem(JSONUtils.getJsonObject(json, "output"));
            String type = JSONUtils.getString(json, "type");
            if (!type.equals("endless:neutronium")){
                throw new IllegalStateException("Type is not found");
            }
            return new NeutroniumRecipe(recipeId, input, count, output);
        }

        @Nullable
        @Override
        public NeutroniumRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            ItemStack input = buffer.readItemStack();
            int count = buffer.readInt();
            ItemStack output = buffer.readItemStack();
            String type = buffer.readString();
            if (!type.equals("endless:neutronium")){
                throw new IllegalStateException("Type is not found");
            }
            return new NeutroniumRecipe(recipeId, input, count, output);
        }

        @Override
        public void write(PacketBuffer buffer, NeutroniumRecipe recipe) {
            buffer.writeItemStack(recipe.input);
            buffer.writeInt(recipe.count);
            buffer.writeItemStack(recipe.output);
        }
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        if (inv.getStackInSlot(0).getItem() == input.getItem()) return true;
        else return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.withSize(1, Ingredient.fromStacks(input));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return this.output.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.NEUTRONIUM.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return EndlessRecipeType.NEUTRONIUM;
    }

    //获取数量
    public int getCount(){
        return count;
    }

    //从json中获取物品
    public static ItemStack deserializeItem(JsonObject object) {
        String s = JSONUtils.getString(object, "item");
        Item item = Registry.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });
        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = JSONUtils.getInt(object, "count", 1);
            return net.minecraftforge.common.crafting.CraftingHelper.getItemStack(object, true);
        }
    }
}
