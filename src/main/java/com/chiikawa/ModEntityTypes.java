package com.chiikawa;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES=
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,chiikawamod.MODID);
    public static final RegistryObject<EntityType<ChiikawEntity>> CHOMPER=
            ENTITY_TYPES.register("chii",
                    ()->EntityType.Builder.of(ChiikawEntity::new, MobCategory.MONSTER)
                            .sized(0.4f,1.5f)
                            .build(new ResourceLocation(chiikawamod.MODID,"chii").toString()));
    public static final RegistryObject<EntityType<ChiikawEntity>> CHOMPE=
            ENTITY_TYPES.register("kawa",
                    ()->EntityType.Builder.of(ChiikawEntity::new, MobCategory.MONSTER)
                            .sized(0.4f,1.5f)
                            .build(new ResourceLocation(chiikawamod.MODID,"kawa").toString()));
    public static final RegistryObject<EntityType<KawaEntity>> WSQ=
            ENTITY_TYPES.register("wsq",
                    ()->EntityType.Builder.of(KawaEntity::new, MobCategory.MONSTER)
                            .sized(0.4f,1.5f)
                            .build(new ResourceLocation(chiikawamod.MODID,"wsq").toString()));
    public static void register(IEventBus eventBus){ENTITY_TYPES.register(eventBus);}
}
