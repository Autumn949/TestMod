package com.example.examplemod.tiles;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.example.examplemod.RegisterBlocks.TESTTILE;


public class TestTile extends TileEntity{
    public TestTile() {
        super(TESTTILE.get());
    }
    private ItemStackHandler itemHandler = createHandler();
    private LazyOptional<IItemHandler> handler= LazyOptional.of(()-> itemHandler);

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        super.load(p_230337_1_, p_230337_2_);
        itemHandler.deserializeNBT(p_230337_2_.getCompound("inv"));
    }

    public CompoundNBT save(CompoundNBT p_189515_1_){
        p_189515_1_.put("inv", itemHandler.serializeNBT());
        return super.save(p_189515_1_);
    }

private ItemStackHandler createHandler(){
        return new ItemStackHandler(9){
            @Override
            protected void onContentsChanged(int slot){
super.onContentsChanged(slot);
                setChanged();
            }
        };

}
public void dropContents(World World, BlockPos pos) {
        for(int i =0;i<itemHandler.getSlots();i++){
            World.addFreshEntity(new ItemEntity(World, pos.getX(),pos.getY(),pos.getZ(),itemHandler.getStackInSlot(i)));
        }


    }
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }
}
