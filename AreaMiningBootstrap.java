package de.example.areamining;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.EquipmentSlotGroup;

/**
 * Registriert die Verzauberung "areamining:area_mining" (Stufe 1-3).
 * Muss im Bootstrapper passieren, weil die Enchantment-Registry danach eingefroren ist.
 */
public class AreaMiningBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(
            RegistryEvents.ENCHANTMENT.compose().newHandler(event ->
                event.registry().register(AreaMiningPlugin.ENCHANT_KEY, builder -> builder
                    .description(Component.text("Area Mining"))
                    .supportedItems(event.getOrCreateTag(ItemTypeTagKeys.ENCHANTABLE_MINING))
                    .weight(1)
                    .maxLevel(3)
                    .minimumCost(EnchantmentRegistryEntry.EnchantmentCost.of(30, 10))
                    .maximumCost(EnchantmentRegistryEntry.EnchantmentCost.of(60, 10))
                    .anvilCost(4)
                    .activeSlots(EquipmentSlotGroup.MAINHAND)
                )
            )
        );
    }
}
