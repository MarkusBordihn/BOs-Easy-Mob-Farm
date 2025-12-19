/*
 * Copyright 2025 Markus Bordihn
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

package de.markusbordihn.easymobfarm.data.capture;

import de.markusbordihn.easymobfarm.Constants;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobCaptureCardDefinitionManager {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final String LOG_PREFIX = "[Mob Capture Card Definition Manager]";

  private static final Map<Identifier, MobCaptureCardDefinition> DEFINITIONS =
      new ConcurrentHashMap<>();
  private static final Map<EntityType<?>, MobCaptureCardDefinition> ENTITY_TYPE_DEFINITIONS =
      new ConcurrentHashMap<>();

  private MobCaptureCardDefinitionManager() {}

  public static void setDefinitions(Map<Identifier, MobCaptureCardDefinition> definitions) {
    log.debug(
        "{} Set {} definitions with {}", LOG_PREFIX, definitions.size(), definitions.keySet());
    DEFINITIONS.clear();
    ENTITY_TYPE_DEFINITIONS.clear();
    for (Map.Entry<Identifier, MobCaptureCardDefinition> entry : definitions.entrySet()) {
      addDefinition(entry.getKey(), entry.getValue());
    }
  }

  public static void addDefinition(Identifier entity, MobCaptureCardDefinition definition) {

    // Verify entity type.
    Optional<EntityType<?>> entityType =
        BuiltInRegistries.ENTITY_TYPE.getOptional(definition.entity());
    if (entityType.isEmpty()) {
      log.warn(
          "{} Skipping {} definition {}. Entity type {} not found.",
          LOG_PREFIX,
          entity,
          definition,
          definition.entity());
      log.warn("This is expected for custom and mod entities which are not loaded.");
      return;
    }

    // Add definition to map and cache entity type.
    log.debug("{} Add {} definition: {}", LOG_PREFIX, entity, definition);
    MobCaptureCardDefinition mobCaptureCardDefinition = definition.withEntityType(entityType.get());
    DEFINITIONS.put(entity, mobCaptureCardDefinition);
    ENTITY_TYPE_DEFINITIONS.put(entityType.get(), mobCaptureCardDefinition);
  }

  public static MobCaptureCardDefinition get(Identifier entity) {
    return DEFINITIONS.get(entity);
  }

  public static MobCaptureCardDefinition get(EntityType<?> entityType) {
    return ENTITY_TYPE_DEFINITIONS.get(entityType);
  }

  public static boolean has(Identifier entity) {
    return DEFINITIONS.containsKey(entity);
  }

  public static Set<Identifier> getDefinedEntities() {
    return DEFINITIONS.keySet();
  }

  public static Map<Identifier, MobCaptureCardDefinition> getAll() {
    return Collections.unmodifiableMap(DEFINITIONS);
  }

  public static void clear() {
    log.debug("{} Clear {} definitions ...", LOG_PREFIX, DEFINITIONS.size());
    DEFINITIONS.clear();
  }
}
