package de.example.areamining;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.keys.EnchantmentKeys;
import net.kyori.adventure.key.Key;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

public class AreaMiningPlugin extends JavaPlugin {

    public static final Key KEY = Key.key("areamining", "area_mining");
    public static final TypedKey<Enchantment> ENCHANT_KEY = EnchantmentKeys.create(KEY);

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new AreaMiningListener(), this);
        getLogger().info("AreaMining aktiv. Nutze: /enchant @s areamining:area_mining 1-3");
    }

    public static Enchantment enchantment() {
        return RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).get(KEY);
    }
}
