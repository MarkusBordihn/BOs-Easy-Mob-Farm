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

package de.markusbordihn.easymobfarm.item;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.loot.LootManager;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CapturedMob extends Item {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected static final String ENTITY_COLOR_TAG = "EntityColor";
  protected static final String ENTITY_ID_TAG = "EntityId";
  protected static final String ENTITY_LOOT_TABLE_TAG = "EntityLootTable";
  protected static final String ENTITY_NAME_TAG = "EntityName";
  protected static final String ENTITY_POSSIBLE_LOOT_TAG = "EntityPossibleLoot";
  protected static final String ENTITY_SHEARED = "EntitySheared";
  protected static final String ENTITY_SIZE_TAG = "EntitySize";
  protected static final String ENTITY_SUB_TYPE_TAG = "EntitySubType";
  protected static final String ENTITY_TYPE_TAG = "EntityType";

  protected static final String FALL_DISTANCE_TAG = "FallDistance";
  protected static final String FIRE_TAG = "Fire";
  protected static final String MOTION_TAG = "Motion";
  protected static final String ON_GROUND_TAG = "OnGround";

  private static Gson gson = new Gson();

  protected final Random random = new Random();

  public CapturedMob(Item.Properties properties) {
    super(properties);
  }

  public static boolean hasCapturedMob(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    return compoundTag.contains(ENTITY_NAME_TAG);
  }

  public static List<String> getPossibleLoot(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains(ENTITY_POSSIBLE_LOOT_TAG)) {
      String possibleLootString = compoundTag.getString(ENTITY_POSSIBLE_LOOT_TAG);
      if (!possibleLootString.isBlank()) {
        return gson.fromJson(possibleLootString, new TypeToken<List<String>>() {
        }.getType());
      }
    }
    return Lists.newArrayList();
  }

  public static String getCapturedMob(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains(ENTITY_NAME_TAG)) {
      return compoundTag.getString(ENTITY_NAME_TAG);
    }
    return "";
  }

  public static String getCapturedMobName(ItemStack itemStack) {
    return getCapturedMob(itemStack);
  }

  public static Float getCapturedMobHealth(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains("Health")) {
      return compoundTag.getFloat("Health");
    }
    return 5.0f;
  }

  public static String getCapturedMobType(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains(ENTITY_TYPE_TAG)) {
      return compoundTag.getString(ENTITY_TYPE_TAG);
    }
    return "";
  }

  public static String getCapturedMobSubType(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains(ENTITY_SUB_TYPE_TAG)) {
      return compoundTag.getString(ENTITY_SUB_TYPE_TAG);
    }
    return "";
  }

  public static EntityType<?> getCapturedMobEntityType(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains(ENTITY_TYPE_TAG)) {
      String entityTypeName = compoundTag.getString(ENTITY_TYPE_TAG);
      ResourceLocation resourceLocation = new ResourceLocation(entityTypeName);
      return Registry.ENTITY_TYPE.get(resourceLocation);
    }
    return null;
  }

  public static DyeColor getCapturedMobColor(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains(ENTITY_COLOR_TAG)) {
      return DyeColor.byId(compoundTag.getInt(ENTITY_COLOR_TAG));
    }
    return null;
  }

  public static boolean getCapturedMobShearedStatus(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains(ENTITY_SHEARED)) {
      return compoundTag.getBoolean(ENTITY_SHEARED);
    }
    return false;
  }

  public static int getCapturedMobSize(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains(ENTITY_SIZE_TAG)) {
      return compoundTag.getInt(ENTITY_SIZE_TAG);
    }
    return 1;
  }

  public static String getLootTable(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains(ENTITY_LOOT_TABLE_TAG)) {
      return compoundTag.getString(ENTITY_LOOT_TABLE_TAG);
    }
    return "";
  }

  public static ItemStack setCapturedMob(LivingEntity livingEntity, ItemStack itemStack) {
    log.debug("Capturing mob {} ...", livingEntity.getName());
    CompoundTag entityData = livingEntity.serializeNBT();
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    String name = livingEntity.getName().getString();
    ResourceLocation registryName = livingEntity.getType().getRegistryName();
    if (registryName == null) {
      log.error("Unable to set captured mob for {} with {}", livingEntity, registryName);
      return itemStack;
    }
    String type = registryName.toString();
    String subType = "";

    // Remove negative effects, which could cause side effects.
    if (entityData.contains(FIRE_TAG) && entityData.getShort(FIRE_TAG) > 0) {
      entityData.putShort(FIRE_TAG, (short) 0);
    }
    if (entityData.contains(FALL_DISTANCE_TAG) && entityData.getFloat(FALL_DISTANCE_TAG) > 0) {
      entityData.putFloat(FALL_DISTANCE_TAG, 0);
    }
    if (entityData.contains(MOTION_TAG)) {
      entityData.put(MOTION_TAG, newDoubleList(0, 0, 0));
    }
    if (entityData.contains(ON_GROUND_TAG) && !entityData.getBoolean(ON_GROUND_TAG)) {
      entityData.putBoolean(ON_GROUND_TAG, true);
    }

    // Store additional data like name, type, ids, colors.
    compoundTag.putString(ENTITY_NAME_TAG, name);
    compoundTag.putString(ENTITY_TYPE_TAG, type);
    compoundTag.putString(ENTITY_ID_TAG, livingEntity.getEncodeId());

    // Store sub type for specific entities like productive bees.
    if ("productivebees:configurable_bee".equals(type) && entityData.contains("type")) {
      String beeType = entityData.getString("type");
      if (beeType != null && !beeType.isEmpty()) {
        subType = beeType;
        compoundTag.putString(ENTITY_SUB_TYPE_TAG, beeType);
      }
    }

    // Store loot table for easier processing.
    ResourceLocation lootTable = livingEntity.getLootTable();

    // Run a second check for alternative loot table to avoid empty loot tables.
    if (lootTable == null || lootTable == BuiltInLootTables.EMPTY) {
      lootTable = livingEntity.getType().getDefaultLootTable();
    }

    // Handle possible loot for tool tips.
    if (lootTable != null) {
      List<String> possibleLoot =
          LootManager.getRandomLootDropOverview(lootTable, livingEntity.getLevel(), type, subType);
      if (possibleLoot != null) {
        compoundTag.putString(ENTITY_POSSIBLE_LOOT_TAG, gson.toJson(possibleLoot));
      }
      compoundTag.putString(ENTITY_LOOT_TABLE_TAG, lootTable.toString());
    }

    // Store color and sheared status for sheep.
    if (livingEntity instanceof Sheep sheep) {
      compoundTag.putInt(ENTITY_COLOR_TAG, sheep.getColor().getId());
      compoundTag.putBoolean(ENTITY_SHEARED, sheep.isSheared());
    }

    // Sore size for slimes.
    if (livingEntity instanceof Slime slime) {
      compoundTag.putInt(ENTITY_SIZE_TAG, slime.getSize());
    }

    compoundTag.merge(entityData);
    itemStack.save(compoundTag);

    // Discarded Entity from the world.
    livingEntity.setRemoved(RemovalReason.DISCARDED);

    return itemStack;
  }

  public static boolean releaseCapturedMob(ItemStack itemStack, BlockPos blockPos, Level level) {
    Entity entity = getCapturedMobEntity(itemStack, level);
    if (entity != null) {
      // Make sure we have an empty Block to spawn the entity, otherwise try above block.
      BlockState blockState = level.getBlockState(blockPos);
      if (!blockState.isAir() && !blockState.is(Blocks.WATER) && !blockState.is(Blocks.GRASS)
          && !blockState.is(Blocks.SEAGRASS)) {
        blockPos = blockPos.above();
        blockState = level.getBlockState(blockPos);
      }

      // Only spawn on empty blocks like air,water, grass, sea grass.
      if (blockState.isAir() || blockState.is(Blocks.WATER) || blockState.is(Blocks.GRASS)
          || blockState.is(Blocks.SEAGRASS)) {

        // Adjust entity position to spawn position.
        entity.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);

        // Add entity to the world.
        log.debug("Release captured mob {} with {}", entity);
        return level.addFreshEntity(entity);
      }
    }
    return false;
  }

  public static Entity getCapturedMobEntity(ItemStack itemStack, Level level) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();

    // Recreated captured mob
    EntityType<?> entityType = getCapturedMobEntityType(itemStack);
    if (entityType == null) {
      return null;
    }

    Entity entity = entityType.create(level);

    if (entity != null) {
      // Remove custom compoundTag data from mob entity
      compoundTag.remove(ENTITY_ID_TAG);
      compoundTag.remove(ENTITY_LOOT_TABLE_TAG);
      compoundTag.remove(ENTITY_NAME_TAG);
      compoundTag.remove(ENTITY_POSSIBLE_LOOT_TAG);
      compoundTag.remove(ENTITY_TYPE_TAG);
      compoundTag.remove(ENTITY_COLOR_TAG);
      compoundTag.remove(ENTITY_SHEARED);
      compoundTag.remove(ENTITY_SIZE_TAG);
      entity.load(compoundTag);

      // Remove compoundTag data from item.
      int damagedValue = itemStack.getDamageValue();
      itemStack.setTag(new CompoundTag());
      itemStack.setDamageValue(damagedValue);
    }
    return entity;
  }

  public static boolean willItemBreak(ItemStack itemStack, int damage) {
    return itemStack.isDamageableItem()
        && itemStack.getDamageValue() + damage >= itemStack.getMaxDamage();
  }

  public static void damageItem(ItemStack itemStack, int damage) {
    int itemStackDamage = itemStack.getDamageValue();
    if (itemStackDamage + damage >= itemStack.getMaxDamage()) {
      itemStack.shrink(1);
    } else {
      itemStack.setDamageValue(itemStackDamage + damage);
    }
  }

  protected static ListTag newDoubleList(double... values) {
    ListTag listTag = new ListTag();
    for (double value : values) {
      listTag.add(DoubleTag.valueOf(value));
    }
    return listTag;
  }

}
