package me.darkeyedragon.enchants.enchant.rare;

import me.darkeyedragon.enchants.enchant.CustomWeaponEnchantment;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Map;

public class MultiShotEnchantment extends CustomWeaponEnchantment {

    public MultiShotEnchantment(int id, String name, String description, TierType tierType, int startingLevel, int maxLevel) {
        super(id, name, description, tierType, startingLevel, maxLevel);
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return other instanceof MultiShotEnchantment;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item.getType().name().endsWith("BOW")) {
            return super.canEnchantItem(item);
        }
        return false;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.WEAPON;
    }

    @Override
    public void hitTarget(Player source, Entity target, ItemStack weapon, CustomWeaponEnchantment weaponEnchantment, int enchantmentLevel) {
        //Ignored
    }

    @EventHandler
    private void onBowShoot(EntityShootBowEvent event) {

        Map<Enchantment, Integer> enchantments = event.getBow().getEnchantments();
        enchantments.forEach((enchantment, integer) -> {
            if (enchantment instanceof MultiShotEnchantment) {
                LivingEntity entity = event.getEntity();
                Location eye = entity.getEyeLocation();

                Vector velocity = event.getProjectile().getVelocity();
                Vector entityRight = rotateAroundY(new Vector(1, 0, 0),Math.PI - eye.getYaw() * Math.PI / 180);
                Vector rotateAxis = velocity.getCrossProduct(entityRight).normalize();

                double angle = Math.PI / 12;
                Vector leftArrow = rotateAroundAxis(velocity.clone(), rotateAxis, angle);
                Vector rightArrow = rotateAroundAxis(velocity.clone(), rotateAxis, -angle);

                entity.getWorld().spawnArrow(eye, leftArrow, 3 * event.getForce(), 0);
                entity.getWorld().spawnArrow(eye, rightArrow, 3 * event.getForce(), 0);
            }
        });

    }

    private Vector rotateAroundY(Vector vector, double angle){
        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);

        double x = angleCos * vector.getX() + angleSin * vector.getZ();
        double y = -angleSin * vector.getX() + angleCos * vector.getZ();
        return vector.clone().setX(x).setZ(y);
    }

    private Vector rotateAroundAxis(Vector vector, Vector axis, double angle) {
        double x = vector.getX(), y = vector.getY(), z = vector.getZ();
        double x2 = axis.getX(), y2 = axis.getY(), z2 = axis.getZ();

        double cosTheta = Math.cos(angle);
        double sinTheta = Math.sin(angle);
        double dotProduct = vector.dot(axis);

        double xPrime = x2 * dotProduct * (1d - cosTheta)
                + x * cosTheta
                + (-z2 * y + y2 * z) * sinTheta;
        double yPrime = y2 * dotProduct * (1d - cosTheta)
                + y * cosTheta
                + (z2 * x - x2 * z) * sinTheta;
        double zPrime = z2 * dotProduct * (1d - cosTheta)
                + z * cosTheta
                + (-y2 * x + x2 * y) * sinTheta;

        return vector.clone().setX(xPrime).setY(yPrime).setZ(zPrime);
    }
}
