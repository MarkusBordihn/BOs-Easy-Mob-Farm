/**
 * Copyright 2021 Markus Bordihn
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.loot.LootManager;

public class CapturedMobItem extends Item {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final String ENTITY_ID_TAG = "EntityId";
  private static final String ENTITY_LOOT_TABLE_TAG = "EntityLootTable";
  private static final String ENTITY_NAME_TAG = "EntityName";
  private static final String ENTITY_POSSIBLE_LOOT_TAG = "EntityPossibleLoot";
  private static final String ENTITY_PROCESSING_TIME_TAG = "EntityProcessingTime";
  private static final String ENTITY_TYPE_TAG = "EntityType";
  private static final String ENTITY_COLOR_TAG = "EntityColor";

  private static final int DEFAULT_FARM_PROCESSING_TIME = 6000;

  private Gson gson = new Gson();

  protected final Random random = new Random();

  public CapturedMobItem(Item.Properties properties) {
    super(properties);
  }

  public boolean hasCapturedMob(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    return compoundTag.contains(ENTITY_NAME_TAG);
  }

  public List<String> getPossibleLoot(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains(ENTITY_POSSIBLE_LOOT_TAG)) {
      String possibleLootString = compoundTag.getString(ENTITY_POSSIBLE_LOOT_TAG);
      if (!possibleLootString.isBlank()) {
        List<String> possibleLoot = gson.fromJson(possibleLootString, ArrayList.class);
        return possibleLoot;
      }
    }
    return Lists.newArrayList();
  }

  public String getCapturedMob(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains(ENTITY_NAME_TAG)) {
      return compoundTag.getString(ENTITY_NAME_TAG);
    }
    return "";
  }

  public Float getCapturedMobHealth(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains("Health")) {
      return compoundTag.getFloat("Health");
    }
    return 5.0f;
  }

  public String getCapturedMobType(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains(ENTITY_TYPE_TAG)) {
      return compoundTag.getString(ENTITY_TYPE_TAG);
    }
    return "";
  }

  public DyeColor getCapturedMobColor(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains(ENTITY_COLOR_TAG)) {
      return DyeColor.byId(compoundTag.getInt(ENTITY_COLOR_TAG));
    }
    return null;
  }

  public int getFarmProcessingTime(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains(ENTITY_PROCESSING_TIME_TAG)) {
      return compoundTag.getInt(ENTITY_PROCESSING_TIME_TAG);
    }
    return DEFAULT_FARM_PROCESSING_TIME;
  }

  public String getLootTable(ItemStack itemStack) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    if (compoundTag.contains(ENTITY_LOOT_TABLE_TAG)) {
      return compoundTag.getString(ENTITY_LOOT_TABLE_TAG);
    }
    return "";
  }

  public ItemStack setCapturedMob(LivingEntity livingEntity, ItemStack itemStack) {
    log.debug("Capturing mob {} ...", livingEntity.getName());
    CompoundTag entityData = livingEntity.serializeNBT();
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    String name = livingEntity.getName().getString();
    String type = livingEntity.getType().getRegistryName().toString();

    // Handle possible loot for tool tips
    ResourceLocation lootTable = livingEntity.getLootTable();
    if (lootTable != null) {
      List<String> possibleLoot =
          LootManager.getRandomLootDropOverview(lootTable, livingEntity.getLevel(), type);
      if (possibleLoot != null) {
        compoundTag.putString(ENTITY_POSSIBLE_LOOT_TAG, gson.toJson(possibleLoot));
      }
      compoundTag.putString(ENTITY_LOOT_TABLE_TAG, lootTable.toString());
    }

    compoundTag.putString(ENTITY_NAME_TAG, name);
    compoundTag.putString(ENTITY_TYPE_TAG, type);
    compoundTag.putString(ENTITY_ID_TAG, livingEntity.getEncodeId());
    if (livingEntity instanceof Sheep sheep) {
      compoundTag.putInt(ENTITY_COLOR_TAG, sheep.getColor().getId());
    }
    compoundTag.merge(entityData);
    itemStack.save(compoundTag);

    // Discarded Entity from the world
    livingEntity.setRemoved(RemovalReason.DISCARDED);

    return itemStack;
  }

  public boolean releaseCapturedMob(ItemStack itemStack, BlockPos blockPos, Level level) {
    Entity entity = getCapturedMobEntity(itemStack, level);
    if (entity != null) {
      // Adjust entity position to spawn position.
      entity.setPosRaw(blockPos.getX(), blockPos.getY(), blockPos.getZ());

      // Add entity to the world.
      log.debug("Release captured mob {} with {}", entity);
      return level.addFreshEntity(entity);
    }
    return false;
  }

  public Entity getCapturedMobEntity(ItemStack itemStack, Level level) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    String entityTypeName = compoundTag.getString(ENTITY_TYPE_TAG);
    ResourceLocation resourceLocation = new ResourceLocation(entityTypeName);

    // Recreated captured mob
    log.debug("Recreate captured mob {} with {}", entityTypeName, compoundTag);
    EntityType<?> entityType = Registry.ENTITY_TYPE.get(resourceLocation);
    Entity entity = entityType.create(level);

    if (entity != null) {
      entity.load(compoundTag);

      // Remove compoundTag data from item.
      int damagedValue = itemStack.getDamageValue();
      itemStack.setTag(new CompoundTag());
      itemStack.setDamageValue(damagedValue);
    }
    return entity;
  }

  public boolean willItemBreak(ItemStack itemStack, int damage) {
    return itemStack.getDamageValue() + damage >= itemStack.getMaxDamage();
  }

  public void damageItem(ItemStack itemStack, int damage) {
    int itemStackDamage = itemStack.getDamageValue();
    if (itemStackDamage + damage >= itemStack.getMaxDamage()) {
      itemStack.shrink(1);
    } else {
      itemStack.setDamageValue(itemStackDamage + damage);
    }
  }

}
