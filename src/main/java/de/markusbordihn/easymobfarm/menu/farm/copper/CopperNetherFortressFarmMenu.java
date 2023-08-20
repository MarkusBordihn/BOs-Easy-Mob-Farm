/**
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

package de.markusbordihn.easymobfarm.menu.farm.copper;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntityData;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperNetherFortressFarm;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
import de.markusbordihn.easymobfarm.menu.ModMenuTypes;

public class CopperNetherFortressFarmMenu extends MobFarmMenu {

  public CopperNetherFortressFarmMenu(int windowIdIn, Inventory inventory) {
    super(windowIdIn, inventory, new SimpleContainer(containerSize),
        new SimpleContainerData(MobFarmBlockEntityData.DATA_SIZE),
        ModMenuTypes.COPPER_NETHER_FORTRESS_FARM_MENU.get());
  }

  public CopperNetherFortressFarmMenu(final int windowId, final Inventory playerInventory,
      final Container container, final ContainerData containerData) {
    super(windowId, playerInventory, container, containerData,
        ModMenuTypes.COPPER_NETHER_FORTRESS_FARM_MENU.get());
  }

  @Override
  public String getAcceptedMobTypeName() {
    return CopperNetherFortressFarm.NAME;
  }

}
