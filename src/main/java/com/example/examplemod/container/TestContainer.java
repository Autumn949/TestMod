package com.example.examplemod.container;

import com.example.examplemod.RegisterBlocks;
import com.example.examplemod.tiles.TestTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

import static com.example.examplemod.RegisterBlocks.TESTBLOCK;
import static com.example.examplemod.RegisterBlocks.TESTCONTAINER;

public class TestContainer extends Container {
    public final TestTile te;
    private final IWorldPosCallable canInteract;

    public TestContainer(int p_i50105_2_, final PlayerInventory playerinv, TestTile te) {
        super(TESTCONTAINER.get(), p_i50105_2_);
        this.te =te;
        this.canInteract = IWorldPosCallable.create(te.getLevel(),te.getBlockPos());

        for(int col =0; col < 9;col++) {
            int finalCol = col;
            te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h->this.addSlot(new SlotItemHandler(h, finalCol, 8 + finalCol * 18, 40)));
        }
        for(int row = 0; row < 3; row++){
            for(int col =0; col < 9; col++){
                this.addSlot(new Slot(playerinv, col + (row * 9) + 9, 8+col *18, 166-(4-row)*18-10));
            }
        }
        for(int col =0; col < 9; col++){
            this.addSlot(new Slot(playerinv, col, 8+col *18, 142));
        }

    }
public TestContainer(final int winid, final PlayerInventory playerInv, final PacketBuffer data){
        this(winid, playerInv, getTileEntity(playerInv,data));
}
    public static TestTile getTileEntity(final PlayerInventory inv, final PacketBuffer data){
        Objects.requireNonNull(inv);
        Objects.requireNonNull(data);
        final TileEntity te = inv.player.level.getBlockEntity(data.readBlockPos());
        if(te instanceof TestTile){
            return (TestTile)te ;
        }
        throw new IllegalStateException("Invalid Tile Entity");

    }
    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
       return stillValid(canInteract,p_75145_1_, TESTBLOCK.get());
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_82846_2_);
        if (slot != null && slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            stack = stack1.copy();
            if (p_82846_2_ < 9
                    && !this.moveItemStackTo(stack1, 9, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
            if (!this.moveItemStackTo(stack1, 0, 9, false)) {
                return ItemStack.EMPTY;
            }

            if (stack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return stack;
    }

    }

