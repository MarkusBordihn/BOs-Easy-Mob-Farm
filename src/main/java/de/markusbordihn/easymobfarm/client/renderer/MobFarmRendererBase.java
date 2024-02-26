/*
 * Copyright 2022 Markus Bordihn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.markusbordihn.easymobfarm.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.client.renderer.helper.RenderHelper;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobFarmRendererBase<T extends MobFarmBlockEntity> implements BlockEntityRenderer<T> {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected Minecraft minecraft;
  protected Level level = null;
  protected BlockEntityRendererProvider.Context context = null;

  private LocalPlayer player;

  private Map<Integer, RenderHelper> renderHelperCache = new ConcurrentHashMap<>();

  public MobFarmRendererBase(BlockEntityRendererProvider.Context context) {
    this.context = context;
    this.minecraft = Minecraft.getInstance();
  }

  public RenderHelper getRenderHelper(int renderId, BlockEntity blockEntity) {
    return renderHelperCache.computeIfAbsent(
        renderId,
        key -> {
          return new RenderHelper(renderId, this.minecraft, blockEntity);
        });
  }

  public void resetRenderHelper(int renderId) {
    if (renderHelperCache.getOrDefault(renderId, null) != null) {
      renderHelperCache.remove(renderId);
    }
  }

  public LocalPlayer getPlayer() {
    if (this.player == null) {
      this.player = this.minecraft.player;
    }
    return this.player;
  }

  @Override
  public void render(
      T blockEntity,
      float partialTicks,
      PoseStack poseStack,
      MultiBufferSource buffer,
      int combinedLight,
      int combinedOverlay) {}
}
