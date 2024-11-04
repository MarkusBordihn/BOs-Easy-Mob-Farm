package de.markusbordihn.easymobfarm.gametest;

import de.markusbordihn.easymobfarm.Constants;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

@SuppressWarnings("unused")
public class SmokeTest {

  @GameTest(template = "easy_mob_farm:gametest.3x3x3")
  public void testModRegistered(GameTestHelper helper) {
    GameTestHelpers.assertTrue(
        helper,
        "Mod " + Constants.MOD_ID + " is not available!",
        FabricLoader.getInstance().isModLoaded(Constants.MOD_ID));
    helper.succeed();
  }
}
