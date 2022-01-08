package de.markusbordihn.easymobfarm.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.menu.AnimalPlainsFarmMenu;

public class AnimalPlainsFarmScreen extends MobFarmScreen<AnimalPlainsFarmMenu> {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final ResourceLocation TEXTURE =
      new ResourceLocation(Constants.MOD_ID, "textures/container/animal_plans_farm_gui.png");
  private static final ResourceLocation SNAP =
      new ResourceLocation(Constants.MOD_ID, "textures/container/snaps/pig_snap.png");

  public AnimalPlainsFarmScreen(AnimalPlainsFarmMenu menu, Inventory inventory,
      Component component) {
    super(menu, inventory, component);
  }

  @Override
  public void init() {
    super.init();
    this.backgroundTexture = TEXTURE;
  }

  @Override
  protected void renderSnap(PoseStack poseStack) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, SNAP);

    // Scale snap image (34x53)
    blit(poseStack, leftPos + 6, topPos + 22, 0, 0, SNAP_WITH, SNAP_HEIGHT, SNAP_WITH, SNAP_HEIGHT);
  }
}
