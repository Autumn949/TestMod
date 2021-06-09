package com.example.examplemod.container;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.RegisterBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.Optional;

public class TestCustomCrafterContainer extends Container {
    private final CraftingInventory craftSlots;
    private final PlayerEntity player;
    private final CraftResultInventory resultSlots;
    private final IWorldPosCallable access;



    public TestCustomCrafterContainer(int p_i50090_1_, PlayerInventory p_i50090_2_, PacketBuffer packet) {
        this(p_i50090_1_,p_i50090_2_,getPos(p_i50090_2_, packet));
    }

    public TestCustomCrafterContainer(int p_i50090_1_, PlayerInventory p_i50090_2_, IWorldPosCallable p_i50090_3_) {
        super(RegisterBlocks.TESTCRAFTINGBLOCKCONTAINER.get(),p_i50090_1_);
        this.craftSlots= new CraftingInventory(this, 4, 4);
        this.resultSlots = new CraftResultInventory();
        this.access = p_i50090_3_;
        this.player = p_i50090_2_.player;


        this.addSlot(new CraftingResultSlot(p_i50090_2_.player, this.craftSlots, this.resultSlots, 0, 124, 35));

        for(int row = 0;row < 4;row++){
            for(int col = 0; col < 4;col++){
                this.addSlot(new Slot(craftSlots, col + row*4, 8+col*18,90-(4-row)*18-10 ));
            }
        }

        for(int row = 0; row < 3; row++){
            for(int col =0; col < 9; col++){
                this.addSlot(new Slot(p_i50090_2_, col + (row * 9) + 9, 8+col *18, 166-(4-row)*18-10));
            }
        }
        for(int col =0; col < 9; col++){
            this.addSlot(new Slot(p_i50090_2_, col, 8+col *18, 142));
        }

    }


    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return stillValid(access,p_75145_1_,RegisterBlocks.TESTCUSTOMCRAFTERBLOCK.get());
    }

    public void slotsChanged(IInventory p_75130_1_) {
        this.access.execute((p_217069_1_, p_217069_2_) -> {
            slotChangedCraftingGrid(this.containerId, p_217069_1_, this.player, this.craftSlots, this.resultSlots);
        });
    }

    protected static void slotChangedCraftingGrid(int p_217066_0_, World p_217066_1_, PlayerEntity p_217066_2_, CraftingInventory p_217066_3_, CraftResultInventory p_217066_4_) {
        if (!p_217066_1_.isClientSide) {
            ServerPlayerEntity lvt_5_1_ = (ServerPlayerEntity)p_217066_2_;
            ItemStack lvt_6_1_ = ItemStack.EMPTY;
            Optional<ICraftingRecipe> lvt_7_1_ = p_217066_1_.getServer().getRecipeManager().getRecipeFor(RegisterBlocks.TESTRECIPETYPE, p_217066_3_, p_217066_1_);
            ExampleMod.LOGGER.info(lvt_7_1_.isPresent());
            if (lvt_7_1_.isPresent()) {
                ICraftingRecipe lvt_8_1_ = lvt_7_1_.get();
                if (p_217066_4_.setRecipeUsed(p_217066_1_, lvt_5_1_, lvt_8_1_)) {
                    lvt_6_1_ = lvt_8_1_.assemble(p_217066_3_);
                }
            }

            p_217066_4_.setItem(0, lvt_6_1_);
            lvt_5_1_.connection.send(new SSetSlotPacket(p_217066_0_, 0, lvt_6_1_));
        }
    }

    public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_) {
        ItemStack lvt_3_1_ = ItemStack.EMPTY;
        Slot lvt_4_1_ = this.slots.get(p_82846_2_);
        if (lvt_4_1_ != null && lvt_4_1_.hasItem()) {
            ItemStack lvt_5_1_ = lvt_4_1_.getItem();
            lvt_3_1_ = lvt_5_1_.copy();
            if (p_82846_2_ == 0) {
                this.access.execute((p_217067_2_, p_217067_3_) -> {
                    lvt_5_1_.getItem().onCraftedBy(lvt_5_1_, p_217067_2_, p_82846_1_);
                });
                if (!this.moveItemStackTo(lvt_5_1_, 10, 52, true)) {
                    return ItemStack.EMPTY;
                }

                lvt_4_1_.onQuickCraft(lvt_5_1_, lvt_3_1_);
            } else if (p_82846_2_ >= 10 && p_82846_2_ < 52) {
                if (!this.moveItemStackTo(lvt_5_1_, 1, 10, false)) {
                    if (p_82846_2_ < 37) {
                        if (!this.moveItemStackTo(lvt_5_1_, 37, 52, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(lvt_5_1_, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(lvt_5_1_, 10, 52, false)) {
                return ItemStack.EMPTY;
            }

            if (lvt_5_1_.isEmpty()) {
                lvt_4_1_.set(ItemStack.EMPTY);
            } else {
                lvt_4_1_.setChanged();
            }

            if (lvt_5_1_.getCount() == lvt_3_1_.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack lvt_6_1_ = lvt_4_1_.onTake(p_82846_1_, lvt_5_1_);
            if (p_82846_2_ == 0) {
                p_82846_1_.drop(lvt_6_1_, false);
            }
        }

        return lvt_3_1_;
    }

    public static IWorldPosCallable getPos(PlayerInventory P, PacketBuffer data){
        Objects.requireNonNull(data);
        final IWorldPosCallable pos = IWorldPosCallable.create(P.player.getCommandSenderWorld(), data.readBlockPos());
        return pos;

    }

    @Override
    public void removed(PlayerEntity p_75134_1_) {
        super.removed(p_75134_1_);
        this.access.execute((p_217068_2_, p_217068_3_) -> {
            this.clearContainer(p_75134_1_, p_217068_2_, this.craftSlots);
        });
    }



}
