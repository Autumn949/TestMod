package com.example.examplemod.serializer;

import com.example.examplemod.RegisterBlocks;
import com.example.examplemod.container.TestCustomCrafterContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class TestCrafterRecipe implements IRecipe<IInventory> {

    @Nonnull private final ItemStack resultitem;
    @Nonnull private final ResourceLocation id;

    public TestCrafterRecipe(ItemStack output, ResourceLocation r)
    {
        this.resultitem=output;
        this.id = r;

    }


    @Override
    public boolean matches(IInventory p_77569_1_, World p_77569_2_) {
        return true;
    }

    @Override
    public ItemStack assemble(IInventory p_77572_1_) {
        return getResultItem();
    }


    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return resultitem;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }


    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RegisterBlocks.TESTSERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return RegisterBlocks.TESTRECIPETYPE;
    }
}
