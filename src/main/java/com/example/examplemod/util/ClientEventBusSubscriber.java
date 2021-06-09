package com.example.examplemod.util;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.RegisterBlocks;
import com.example.examplemod.screen.TestCraftingScreen;
import com.example.examplemod.screen.TestCustomCrafterScreen;
import com.example.examplemod.screen.TestScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID,bus= Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        ScreenManager.register(RegisterBlocks.TESTCONTAINER.get(), TestScreen::new);
        ScreenManager.register(RegisterBlocks.TESTCRAFTINGBLOCKCONTAINER.get(), TestCraftingScreen::new);
        ScreenManager.register(RegisterBlocks.TESTCUSTOMCRAFTERCONTAINER.get(), TestCustomCrafterScreen::new);
    }
}
