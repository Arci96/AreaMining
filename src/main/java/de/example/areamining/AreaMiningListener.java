package de.example.areamining;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AreaMiningListener implements Listener {

    /** Verhindert Endlosschleifen, weil breakBlock() selbst BlockBreakEvents ausloest. */
    private final Set<UUID> working = new HashSet<>();

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        UUID id = player.getUniqueId();

        if (working.contains(id) || player.isSneaking()) return; // Schleichen = nur 1 Block

        ItemStack tool = player.getInventory().getItemInMainHand();
        if (tool.isEmpty()) return;

        String type = tool.getType().name();
        if (!type.endsWith("_PICKAXE") && !type.endsWith("_SHOVEL")) return;

        Enchantment enchantment = AreaMiningPlugin.enchantment();
        if (enchantment == null) return;

        int level = tool.getEnchantmentLevel(enchantment);
        if (level <= 0) return;

        int size = Math.min(level, 3) + 2; // 1 -> 3x3, 2 -> 4x4, 3 -> 5x5

        RayTraceResult ray = player.rayTraceBlocks(8);
        if (ray == null || ray.getHitBlockFace() == null) return;
        BlockFace face = ray.getHitBlockFace();

        Block center = event.getBlock();
        int lo = -(size - 1) / 2; // 3: -1, 4: -1, 5: -2
        int hi = size / 2;        // 3:  1, 4:  2, 5:  2

        working.add(id);
        try {
            for (int a = lo; a <= hi; a++) {
                for (int b = lo; b <= hi; b++) {
                    if (a == 0 && b == 0) continue;

                    Block target;
                    if (face.getModX() != 0) {
                        target = center.getRelative(0, a, b);
                    } else if (face.getModY() != 0) {
                        target = center.getRelative(a, 0, b);
                    } else {
                        target = center.getRelative(a, b, 0);
                    }

                    if (!canBreak(target, tool)) continue;

                    player.breakBlock(target); // loest Events aus (Schutz-Plugins), gibt Drops, schadet Werkzeug

                    tool = player.getInventory().getItemInMainHand();
                    if (tool.isEmpty()) return; // Werkzeug kaputt gegangen
                }
            }
        } finally {
            working.remove(id);
        }
    }

    private boolean canBreak(Block block, ItemStack tool) {
        Material m = block.getType();
        if (m.isAir() || block.isLiquid()) return false;
        if (m.getHardness() < 0) return false;                // Bedrock etc.
        return block.getDestroySpeed(tool) > 1.0f;            // nur Bloecke, die zum Werkzeug passen
    }
}
