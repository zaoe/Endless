package com.yuo.endless.Entity;

import com.yuo.endless.Endless;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Endless.MOD_ID);

    //箭
    public static RegistryObject<EntityType<InfinityArrowEntity>> INFINITY_ARROW = ENTITY_TYPES.register("infinity_arrow",
            () -> EntityType.Builder.<InfinityArrowEntity>create(InfinityArrowEntity::new, EntityClassification.MISC)
            .size(0.5f, 0.5F).build("infinity_arrow"));
    public static RegistryObject<EntityType<InfinityCrossArrowEntity>> INFINITY_CROSS_ARROW = ENTITY_TYPES.register("infinity_cross_arrow",
            () -> EntityType.Builder.<InfinityCrossArrowEntity>create(InfinityCrossArrowEntity::new, EntityClassification.MISC)
                    .size(0.5f, 0.5F).build("infinity_cross_arrow"));
    public static RegistryObject<EntityType<InfinityArrowSubEntity>> INFINITY_ARROW_SUB = ENTITY_TYPES.register("infinity_arrow_sub",
            () -> EntityType.Builder.<InfinityArrowSubEntity>create(InfinityArrowSubEntity::new, EntityClassification.MISC)
            .size(0.5f, 0.5F).build("infinity_arrow_sub"));
    public static RegistryObject<EntityType<EndestPearlEntity>> ENDEST_PEARL = ENTITY_TYPES.register("endest_pearl",
            () -> EntityType.Builder.<EndestPearlEntity>create(EndestPearlEntity::new, EntityClassification.MISC)
            .size(0.5f, 0.5F).build("endest_pearl"));
    public static RegistryObject<EntityType<GapingVoidEntity>> GAPING_VOID = ENTITY_TYPES.register("gaping_void",
            () -> EntityType.Builder.<GapingVoidEntity>create(GapingVoidEntity::new, EntityClassification.MISC)
            .size(0.5f, 0.5F).build("gaping_void"));
    public static RegistryObject<EntityType<InfinityFireWorkEntity>> INFINITY_FIREWORK = ENTITY_TYPES.register("infinity_firework",
            () -> EntityType.Builder.<InfinityFireWorkEntity>create(InfinityFireWorkEntity::new, EntityClassification.MISC)
                    .size(0.5f, 0.5F).build("infinity_firework"));
}
