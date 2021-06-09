package com.example.examplemod;

import com.example.examplemod.blocks.TestCustomCrafter;
import com.example.examplemod.blocks.testCraftingTable;
import com.example.examplemod.blocks.testblock;
import com.example.examplemod.container.TestContainer;
import com.example.examplemod.container.TestCraftingContainer;
import com.example.examplemod.container.TestCustomCrafterContainer;
import com.example.examplemod.screen.TestCraftingScreen;
import com.example.examplemod.serializer.MyRecipeSerializer;
import com.example.examplemod.serializer.TestCrafterRecipe;
import com.example.examplemod.tiles.TestTile;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class RegisterBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ExampleMod.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS,ExampleMod.MODID);
    public static final DeferredRegister<IRecipeSerializer<?>> SERIALIZER_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ExampleMod.MODID);

    public static final RegistryObject<Block>TESTCRAFTINGBLOCK=register("testcraftingblock", ()-> new testCraftingTable(AbstractBlock.Properties.of(Material.BAMBOO)));
    public static final RegistryObject<Block> TESTBLOCK =register("testblock", () -> new testblock(AbstractBlock.Properties.of(Material.BAMBOO)));
    public static final RegistryObject<TileEntityType<TestTile>> TESTTILE = TILES.register("testtile", ()-> TileEntityType.Builder.of(TestTile::new,TESTBLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<TestContainer>> TESTCONTAINER= CONTAINERS.register("testtile",()-> IForgeContainerType.create(TestContainer::new));
    public static final RegistryObject<ContainerType<TestCraftingContainer>> TESTCRAFTINGBLOCKCONTAINER = CONTAINERS.register("testcrafter", ()->IForgeContainerType.create(TestCraftingContainer::new));
    public static final RegistryObject<ContainerType<TestCustomCrafterContainer>> TESTCUSTOMCRAFTERCONTAINER = CONTAINERS.register("testcustomcrafter", ()-> IForgeContainerType.create(TestCustomCrafterContainer::new));
    public static final RegistryObject<MyRecipeSerializer> TESTSERIALIZER = SERIALIZER_DEFERRED_REGISTER.register("customrecipeserializer", MyRecipeSerializer::new);
    public static final IRecipeType TESTRECIPETYPE = IRecipeType.<TestCrafterRecipe>register("testrecipetype");
    public static final RegistryObject<Block> TESTCUSTOMCRAFTERBLOCK = register("testcustomcraftingblock", ()-> new TestCustomCrafter(AbstractBlock.Properties.of(Material.BAMBOO)));
    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TILES.register(modEventBus);
        CONTAINERS.register(modEventBus);
        SERIALIZER_DEFERRED_REGISTER.register(modEventBus);


    }

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }


    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)));
        return ret;
    }
}
