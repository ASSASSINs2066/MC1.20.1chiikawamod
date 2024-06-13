package com.chiikawa;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

import java.util.function.Supplier;

//import java.util.jar.Attributes;
// The value here should match an entry in the META-INF/mods.toml file
@Mod(chiikawamod.MODID)
public class chiikawamod
{
    //你好
    // Define mod id in a common place for everything to reference
    public static final String MODID = "chiikawa";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredRegister<SoundEvent>SOUND_EVENTS=DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,MODID);
    public static final RegistryObject<SoundEvent>CHII_DEATH=registerSoundEvents("entity.chii.death");
    public static final RegistryObject<SoundEvent>CHII_EAT=registerSoundEvents("entity.chii.eat");
    public static final RegistryObject<SoundEvent>CHII_HEART=registerSoundEvents("entity.chii.heart");
    public static final RegistryObject<SoundEvent>CHII_TAKE=registerSoundEvents("entity.chii.take");
    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("new_block", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.SLIME_BLOCK).requiresCorrectToolForDrops().strength(1F,1F)));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("new_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));

    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("new_item",
            () -> new ForgeSpawnEggItem(ModEntityTypes.CHOMPER,0xFFFFFF,0xFFFFFF,new Item.Properties()));
    public static final RegistryObject<Item> KAWA_ITEM = ITEMS.register("kawa_item",
            () -> new ForgeSpawnEggItem(ModEntityTypes.CHOMPE,0xFFFFFF,0xFFFFFF,new Item.Properties()));
    public static final RegistryObject<Item> WSQ_ITEM = ITEMS.register("wsq_item",
            () -> new ForgeSpawnEggItem(ModEntityTypes.WSQ,0xFFFFFF,0xFFFFFF,new Item.Properties()));
    public static  final RegistryObject<Item> NEW_SWORD = ITEMS.register("new_sword",
            ()->new SwordItem(ModItemTiers.RED,3,-2.4F,new Item.Properties()));

    public static final RegistryObject<Item>NEW_AXE=ITEMS.register("new_axe",
            ()->new AxeItem(ModItemTiers.RED,3,-2.8F,new Item.Properties()));
    public  static final RegistryObject<Item>NEW_TRIDENT=ITEMS.register("new_trident",
            ()->new SwordItem(ModItemTiers.RED,4,-2.4F,new Item.Properties()));
    public static final RegistryObject<Item> dust;

    static {
        dust =ITEMS.register("dust",
                ()->new Item(new Item.Properties()));

    }
    //public static final RegistryObject<SoundEvents>CHII_EAT=SOUNDS.register("",new SoundEvent());
    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("new_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the even
                output.accept(KAWA_ITEM.get());
                output.accept(WSQ_ITEM.get());
                output.accept(EXAMPLE_BLOCK.get());
                output.accept(dust.get());
                output.accept(NEW_SWORD.get());
                output.accept(NEW_AXE.get());
                output.accept(NEW_TRIDENT.get());

            }).build());
    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, name)));
    }
    public chiikawamod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(chiikawamod::onGatherData);
        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        GeckoLib.initialize();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(PlayerLogin::onLoggedIn);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static class PlayerLogin{
        public static void onLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
        {
            var player = event.getEntity();
            player.sendSystemMessage(Component.literal("姐妹~姐妹"));
        }
    }
    public static void onGatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var packOutput = gen.getPackOutput();
        var helper = event.getExistingFileHelper();
        gen.addProvider(event.includeClient(), new ChineseLanguageProvider(packOutput));
        gen.addProvider(event.includeClient(), new ModelProvider(packOutput,helper));
        gen.addProvider(event.includeClient(), new StateProvider(packOutput,helper));
    }

    public static class ModelProvider extends ItemModelProvider {
        public ModelProvider(PackOutput gen, ExistingFileHelper helper){
            super(gen,MODID,helper);
        }
        @Override
        protected void registerModels(){
            this.singleTexture("dust",new ResourceLocation("item/generated"),
                    "layer0",new ResourceLocation(MODID,"item/"+"dust"));

            this.singleTexture("new_item",new ResourceLocation("item/generated"),
                    "layer0",new ResourceLocation(MODID,"item/"+"new_item"));

        }
    }
    public static class StateProvider extends BlockStateProvider {
        public StateProvider(PackOutput gen, ExistingFileHelper helper) {
            super(gen, MODID, helper);
        }
        @Override
        protected void registerStatesAndModels() {
            this.simpleBlock(EXAMPLE_BLOCK.get(), this.cubeAll(EXAMPLE_BLOCK.get()));
            this.simpleBlockItem(EXAMPLE_BLOCK.get(), this.cubeAll(EXAMPLE_BLOCK.get()));
        }
    }
    public static class ChineseLanguageProvider extends LanguageProvider{
        public ChineseLanguageProvider(PackOutput gen){
            super(gen,MODID,"zh_cn");
        }
        @Override
        protected void addTranslations(){
            this.add(dust.get(),"粉粉");
            this.add(EXAMPLE_ITEM.get(),"吉伊");
            this.add(EXAMPLE_BLOCK.get(),"粉块");
            this.add(NEW_SWORD.get(),"仙女剑");
            this.add(NEW_AXE.get(),"仙女斧");
            this.add(NEW_TRIDENT.get(),"长枪");
            this.add(KAWA_ITEM.get(),"小八");
            this.add(WSQ_ITEM.get(),"乌萨其");
        }
    }

    public enum ModItemTiers implements Tier {
        RED(3, 500, 10.0F, 8.0F, 15, () -> {
            //返回需要的修复材料
            return Ingredient.of(chiikawamod.dust.get());
        });

        private final int harvestLevel;
        private final int maxUses;
        private final float efficiency;
        private final float attackDamage;
        private final int enchantability;
        private final LazyLoadedValue<Ingredient> repairMaterial;

        ModItemTiers(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
            this.harvestLevel = harvestLevel;
            this.maxUses = maxUses;
            this.efficiency = efficiency;
            this.attackDamage = attackDamage;
            this.enchantability = enchantability;
            this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
        }

        @Override
        public int getUses() {
            return this.maxUses;
        }

        @Override
        public float getSpeed() {
            return this.efficiency;
        }

        @Override
        public float getAttackDamageBonus() {
            return this.attackDamage;
        }

        @Override
        public int getLevel() {
            return this.harvestLevel;
        }

        @Override
        public int getEnchantmentValue() {
            return this.enchantability;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return this.repairMaterial.get();
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        event.enqueueWork(()->{
            SpawnPlacements.register(ModEntityTypes.CHOMPER.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.CHOMPE.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.WSQ.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
                });


        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(EXAMPLE_BLOCK_ITEM);
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        // Do something when the server starts
        //LOGGER.info("HELLO from server starting");
        //var player =event.getEntity();
        //player.sendSystemMessage(Component.literal("兄弟"));

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            EntityRenderers.register(ModEntityTypes.CHOMPER.get(),ChomperRenderer::new);
            EntityRenderers.register(ModEntityTypes.CHOMPE.get(),ChompeRenderer::new);
            EntityRenderers.register(ModEntityTypes.WSQ.get(),KawaRenderer::new);
        }
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event)
        {
            // Some client setup code
           event.put(ModEntityTypes.CHOMPER.get(),ChiikawEntity.setAttributes());
            event.put(ModEntityTypes.CHOMPE.get(),ChiikawEntity.setAttributes());
            event.put(ModEntityTypes.WSQ.get(),KawaEntity.setAttributes());
        }
    }
}
