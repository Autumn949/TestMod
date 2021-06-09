package com.example.examplemod.serializer;

import com.example.examplemod.ExampleMod;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class MyRecipeSerializer
        extends ForgeRegistryEntry<IRecipeSerializer<?>>
        implements IRecipeSerializer<TestCrafterRecipe> {

    @Override
    public TestCrafterRecipe fromJson(ResourceLocation p_199425_1_, JsonObject p_199425_2_) {
        ExampleMod.LOGGER.warn("EXECUTEDFROMJSON");
        return new TestCrafterRecipe(new ItemStack(Items.ACACIA_LEAVES),p_199425_1_);
    }

    @Nullable
    @Override
    public TestCrafterRecipe fromNetwork(ResourceLocation p_199426_1_, PacketBuffer p_199426_2_) {
        ExampleMod.LOGGER.warn("EXECUTEDFROMNETWORK");
        return new TestCrafterRecipe(new ItemStack(Items.ACACIA_LEAVES),p_199426_1_);
    }

    @Override
    public void toNetwork(PacketBuffer p_199427_1_, TestCrafterRecipe p_199427_2_) {

    }

}
