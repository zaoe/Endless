package com.yuo.endless.Armor;

import com.yuo.endless.Items.ItemRegistry;
import com.yuo.endless.Items.Tool.EndlessItemEntity;
import com.yuo.endless.tab.ModGroup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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

	//不会触发末影人仇恨
	@Override
	public boolean isEnderMask(ItemStack stack, PlayerEntity player, EndermanEntity endermanEntity) {
		return true;
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
						player.setAir(300);
					}
					player.getFoodStats().addStats(20, 20f); //饱腹
					if (next.hasTag() && next.getTag().getBoolean("flag"))
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
//								player.removeActivePotionEffect(e);
								player.clearActivePotions();
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

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) { //切换无尽装备模式
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (!worldIn.isRemote && playerIn.isSneaking()){
			CompoundNBT tags = stack.getTag();
			if (tags == null) {
				tags = new CompoundNBT();
				stack.setTag(tags);
			}
			tags.putBoolean("flag", !tags.getBoolean("flag"));
			playerIn.swingArm(handIn); //摆臂
		}
		return ActionResult.resultSuccess(stack);
	}

	//获取盔甲贴图纹理
//	@Nullable
//	@Override
//	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
//		return "endless:textures/models/infinity_armor.png";
//	}

	//盔甲无法损坏
//	@Override
//	public void setDamage(ItemStack stack, int damage) {
//		super.setDamage(stack, 0);
//	}

	@Override
	public boolean isDamageable() {
		return false;
	}

//	private static final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation("textures/entity/enderman/enderman_eyes.png"));

	//渲染盔甲模型
//	@Nullable
//	@Override
//	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
//		_default.getRenderType(new ResourceLocation(Endless.MODID, "textures/models/infinity_armor_eyes.png"));
//		_default.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
//		Minecraft mc = Minecraft.getInstance();
//		RenderTypeBuffers buffers = mc.getRenderTypeBuffers();
//		IVertexBuilder ivertexbuilder = mc.getRenderTypeBuffers().getBufferSource().getBuffer(RENDER_TYPE);
//		_default.bipedHeadwear.render(new MatrixStack(), ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
//		return _default;
//	}

	//不会损坏
//	@Override
//	public int getDamageReduceAmount() {
//		return 1000;
//	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (slot == EquipmentSlotType.HEAD) {
			tooltip.add(new TranslationTextComponent("endless.text.itemInfo.infinity_head"));
			if (stack.hasTag() && stack.getTag().getBoolean("flag"))
				tooltip.add(new TranslationTextComponent("endless.text.itemInfo.infinity_head1"));
		}
		if (slot == EquipmentSlotType.CHEST) {
			tooltip.add(new TranslationTextComponent("endless.text.itemInfo.infinity_chest"));
			if (stack.hasTag() && stack.getTag().getBoolean("flag"))
				tooltip.add(new StringTextComponent(TextFormatting.BLUE + "+" + TextFormatting.ITALIC + "100" +
						TextFormatting.RESET + "" + TextFormatting.BLUE + "% FlySpeed"));
		}
		if (slot == EquipmentSlotType.LEGS) {
			tooltip.add(new TranslationTextComponent("endless.text.itemInfo.infinity_legs"));
			if (stack.hasTag() && stack.getTag().getBoolean("flag"))
				tooltip.add(new StringTextComponent(TextFormatting.BLUE + "+" + TextFormatting.ITALIC + "300" +
						TextFormatting.RESET + "" + TextFormatting.BLUE + "% WalkSpeed"));
		}
		if (slot == EquipmentSlotType.FEET) {
			tooltip.add(new TranslationTextComponent("endless.text.itemInfo.infinity_feet"));
			if (stack.hasTag() && stack.getTag().getBoolean("flag"))
				tooltip.add(new StringTextComponent(TextFormatting.BLUE + "+" + TextFormatting.ITALIC + "400" +
						TextFormatting.RESET + "" + TextFormatting.BLUE + "% JumpHeight"));
		}

	}

	@Nullable
	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		return new EndlessItemEntity(world, location.getPosX(), location.getPosY(), location.getPosZ(), itemstack);
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}

}
