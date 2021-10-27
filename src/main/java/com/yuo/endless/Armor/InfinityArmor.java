package com.yuo.endless.Armor;

import com.yuo.endless.Items.ItemRegistry;
import com.yuo.endless.tab.ModGroup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class InfinityArmor extends ArmorItem{

	private static Properties properties = new Properties().maxStackSize(1).group(ModGroup.myGroup);

	public InfinityArmor(EquipmentSlotType slot) {
		super(MyArmorMaterial.INFINITY, slot, properties);
	}

	//盔甲在身上时触发效果
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		Iterator<ItemStack> iterator = player.getArmorInventoryList().iterator();
		while (iterator.hasNext()){
			ItemStack next = iterator.next();
			if (!next.isEmpty()){
				Item item = next.getItem();
				if (item.equals(ItemRegistry.infinityHead.get())){
					if (player.areEyesInFluid(FluidTags.WATER)){ //玩家视线在水中
						player.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 0, 5));
					}
					player.getFoodStats().addStats(20, 20f); //饱腹
					player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 300, 0)); //夜视
				}
				if (item.equals(ItemRegistry.infinityChest.get())){
					//清除所有负面效果
					Collection<EffectInstance> effects = player.getActivePotionEffects();
					if (effects.size() > 0){
						List<Effect> bad = new ArrayList<>();
						effects.forEach((e) -> {
							if (!e.getPotion().isBeneficial())
								bad.add(e.getPotion());
						});
						if (bad.size() > 0){
							bad.forEach((e) ->{
								player.removeActivePotionEffect(e);
							});
						}
					}
				}
				if (item.equals(ItemRegistry.infinityLegs.get())){
					if (player.isBurning()) player.extinguish();//着火时熄灭
					player.isImmuneToFire(); //免疫火伤
				}
			}
		}
	}

	//获取盔甲贴图纹理
//	@Nullable
//	@Override
//	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
//		return "endless:textures/models/infinity_armor.png";
//	}

	//盔甲无法损坏
	@Override
	public void setDamage(ItemStack stack, int damage) {
		super.setDamage(stack, 0);
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return Rarity.create("COSMIC", TextFormatting.RED);
	}

	//渲染盔甲模型
//	@Nullable
//	@Override
//	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
//		ModelArmorInfinity model = armorSlot == EquipmentSlotType.LEGS ? ModelArmorInfinity.legModel : ModelArmorInfinity.armorModel;
//		model.update(entityLiving, itemStack, armorSlot);
//		return (A) model;
//	}

	//不会损坏
	@Override
	public int getDamageReduceAmount() {
		return 1000;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (slot == EquipmentSlotType.HEAD) {
			tooltip.add(new TranslationTextComponent("endless.text.itemInfo.infinity_head"));
		}
		if (slot == EquipmentSlotType.CHEST) {
			tooltip.add(new TranslationTextComponent("endless.text.itemInfo.infinity_chest"));
		}
		if (slot == EquipmentSlotType.LEGS) {
			tooltip.add(new TranslationTextComponent("endless.text.itemInfo.infinity_legs"));
			tooltip.add(new StringTextComponent(TextFormatting.BLUE + "+" + TextFormatting.ITALIC + "300" + TextFormatting.RESET + "" + TextFormatting.BLUE + "% Speed"));
		}
		if (slot == EquipmentSlotType.FEET) {
			tooltip.add(new TranslationTextComponent("endless.text.itemInfo.infinity_feet"));
			tooltip.add(new StringTextComponent(TextFormatting.BLUE + "+" + TextFormatting.ITALIC + "300" + TextFormatting.RESET + "" + TextFormatting.BLUE + "% Jump"));
		}

	}

}
