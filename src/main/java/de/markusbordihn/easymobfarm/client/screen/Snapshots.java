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

package de.markusbordihn.easymobfarm.client.screen;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.resources.ResourceLocation;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.config.mobs.HostileMonster;
import de.markusbordihn.easymobfarm.config.mobs.HostileWaterMonster;
import de.markusbordihn.easymobfarm.config.mobs.PassiveAnimal;
import de.markusbordihn.easymobfarm.config.mobs.PassiveWaterAnimal;

public class Snapshots {

  protected Snapshots() {}

  private static final String SNAPSHOT_PATH = "textures/container/snaps/";

  protected static final Map<String, ResourceLocation> SnapshotsMap = createSnapshotMap();

  // This way allows a cpu and memory optimized may to create a static map.
  private static Map<String, ResourceLocation> createSnapshotMap() {
    Map<String, ResourceLocation> hashMap = new HashMap<>();
    hashMap.put(HostileMonster.CAVE_SPIDER,
        new ResourceLocation(Constants.MOD_ID, SNAPSHOT_PATH + "minecraft_cave_spider.png"));
    hashMap.put(HostileMonster.CREEPER,
        new ResourceLocation(Constants.MOD_ID, SNAPSHOT_PATH + "minecraft_creeper.png"));
    hashMap.put(HostileMonster.SKELETON,
        new ResourceLocation(Constants.MOD_ID, SNAPSHOT_PATH + "minecraft_skeleton.png"));
    hashMap.put(HostileMonster.SLIME,
        new ResourceLocation(Constants.MOD_ID, SNAPSHOT_PATH + "minecraft_slime.png"));
    hashMap.put(HostileMonster.WITCH,
        new ResourceLocation(Constants.MOD_ID, SNAPSHOT_PATH + "minecraft_witch.png"));
    hashMap.put(HostileMonster.ZOMBIE,
        new ResourceLocation(Constants.MOD_ID, SNAPSHOT_PATH + "minecraft_zombie.png"));
    hashMap.put(HostileWaterMonster.DROWNED,
        new ResourceLocation(Constants.MOD_ID, SNAPSHOT_PATH + "minecraft_drowned.png"));
    hashMap.put(PassiveAnimal.COW,
        new ResourceLocation(Constants.MOD_ID, SNAPSHOT_PATH + "minecraft_cow.png"));
    hashMap.put(PassiveAnimal.PIG,
        new ResourceLocation(Constants.MOD_ID, SNAPSHOT_PATH + "minecraft_pig.png"));
    hashMap.put(PassiveAnimal.SHEEP,
        new ResourceLocation(Constants.MOD_ID, SNAPSHOT_PATH + "minecraft_sheep.png"));
    hashMap.put(PassiveWaterAnimal.SQUID,
        new ResourceLocation(Constants.MOD_ID, SNAPSHOT_PATH + "minecraft_squid.png"));
    return hashMap;
  }

  public static ResourceLocation getSnapshot(String mobFarmType) {
    return SnapshotsMap.get(mobFarmType);
  }
}
