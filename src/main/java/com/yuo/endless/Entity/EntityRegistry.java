package com.yuo.endless.Entity;

import com.yuo.endless.Endless;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Endless.MODID);

    //箭
    public static RegistryObject<EntityType<InfinityArrowEntity>> INFINITY_ARROW = ENTITY_TYPES.register("infinity_arrow", () -> {
        return EntityType.Builder.<InfinityArrowEntity>create(InfinityArrowEntity::new, EntityClassification.MISC)
                .size(0.5f, 0.5F).build("iron_arrow");
    });
}
