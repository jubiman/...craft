package com.jubiman.dungeons.abilities;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import java.util.List;

public class shijutsu {
    public static void call(Player player) {
        List<Entity> nearbyEntities = player.getNearbyEntities(15.0D,15.0D,15.0D);
        int i = 0;
        for (Entity entity : nearbyEntities) {
            if (i > 5) break;
            if (entity instanceof Monster) {
                Monster monster = (Monster) entity;

                // Teleport player behind the monster
                double newX;
                double newZ;
                float nang = monster.getLocation().getYaw() + 90;

                if (nang < 0) nang += 360;

                newX = Math.cos(Math.toRadians(nang));
                newZ = Math.sin(Math.toRadians(nang));

                Location loc = new Location(monster.getWorld(), monster.getLocation().getX() - newX,
                        monster.getLocation().getY(), monster.getLocation().getZ() - newZ,
                        monster.getLocation().getYaw(), monster.getLocation().getPitch() + 10);

                // Teleport the player and play a sound
                player.teleport(loc);
                player.playSound(loc, Sound.ENDERMAN_TELEPORT, 0.5f, 1);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}

                // Force player to attack
                EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

                PlayerConnection connection = entityPlayer.playerConnection;
                PacketPlayOutAnimation armSwing = new PacketPlayOutAnimation(entityPlayer, 0); // '0' is the id for arm swing
                connection.sendPacket(armSwing);
                connection.a(new PacketPlayInArmAnimation()); // Show animation for others too

                Entity attacked = getTargetEntity(player);
                if (attacked != null) {
                    ((CraftPlayer) player).getHandle().attack(((CraftEntity) attacked).getHandle());
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
                ++i;
            }
        }
    }

    private static Entity getTargetEntity(Entity entity) {
        float reachDistance = getReachDistance(entity);
        Vec3D eyeLocation = new Vec3D(entity.getLocation().getX(), entity.getLocation().getY() + ((CraftEntity)entity).getHandle().getHeadHeight(), entity.getLocation().getZ());
        Vec3D direction = getLookVector(entity.getLocation().getPitch(), entity.getLocation().getYaw());
        Vec3D reachVector = multVector(direction, reachDistance);

        Vec3D target = addVectors(eyeLocation, reachVector);
        AxisAlignedBB area = expandAABB(((CraftEntity)entity).getHandle().getBoundingBox(), reachVector).grow(1, 1, 1);
        List<Entity> entities = entity.getNearbyEntities(area.a, area.b, area.c);

        Entity pointedEntity = null;
        double minDistance = reachDistance;
        for (Entity found : entities) {
            double borderSize = getCollisionBorderSize(found);
            AxisAlignedBB aabb = ((CraftEntity)found).getHandle().getBoundingBox().grow(borderSize, borderSize, borderSize);
            MovingObjectPosition intercept = calculateIntercept(aabb, eyeLocation, target);

            if (aabbContains(aabb, eyeLocation)) {
                if (minDistance >= 0.0D) {
                    pointedEntity = found;
                    minDistance = 0.0D;
                }
                continue;
            }
            if (intercept == null) {
                continue;
            }
            double distance = Math.sqrt(eyeLocation.distanceSquared(intercept.pos)); // Math.sqrt() could be left out if you use squared distances instead?

            if (distance < minDistance || minDistance == 0.0D) {
                if (found.getVehicle() == entity.getVehicle()) {
                    if (minDistance == 0.0D) {
                        pointedEntity = found;
                    }
                    continue;
                }
                pointedEntity = found;
                minDistance = distance;
            }
        }
        return pointedEntity;
    }

    // Obfuscation helpers

    private static double getCollisionBorderSize(Entity entity) {
        return ((CraftEntity)entity).getHandle().getBoundingBox().a();
    }

    private static AxisAlignedBB expandAABB(AxisAlignedBB aabb, Vec3D values) {
        // TODO: might be aabb.a()
        return aabb.grow(values.a, values.b, values.c);
    }

    private static MovingObjectPosition calculateIntercept(AxisAlignedBB aabb, Vec3D a, Vec3D b) {
        return aabb.a(a, b);
    }

    private static boolean aabbContains(AxisAlignedBB aabb, Vec3D point) {
        return point.a > aabb.a && point.a < aabb.d && point.b > aabb.b && point.b < aabb.e && point.c > aabb.c
                && point.c < aabb.f;
    }

    // These two exist in Vec3D class but names are obfuscated, I'll rather do it manually
    private static Vec3D multVector(Vec3D vector, double scalar) {
        return new Vec3D(vector.a * scalar, vector.b * scalar, vector.c * scalar);
    }

    private static Vec3D addVectors(Vec3D a, Vec3D b) {
        return new Vec3D(a.a + b.a, a.b + b.b, a.c + b.c);
    }

    private static float getReachDistance(Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            return player.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.CREATIVE ? 5.0f : 4.5f; // values from Forge
        }
        return 4.0F;
    }

    private static Vec3D getLookVector(float pitch, float yaw) {
        float floatPI = (float) Math.PI;
        float x = MathHelper.sin(-yaw * floatPI / 180f - floatPI);
        float z = MathHelper.cos(-yaw * floatPI / 180f - floatPI);
        float zx = -MathHelper.cos(-pitch * floatPI / 180f);
        float y = MathHelper.sin(-pitch * floatPI / 180f);
        return new Vec3D(x * zx, y, z * zx);
    }
}
