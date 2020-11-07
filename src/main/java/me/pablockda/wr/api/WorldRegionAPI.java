package me.pablockda.wr.api;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

import me.pablockda.wr.managers.RegionManager;


public final class WorldRegionAPI {
	
	public static void addRegion(String region, String world) {
		RegionManager.addRegion(region, world);
	}
	
	public static void addRegion(String region, String world, Double ax, Double ay, Double az, Double bx, Double by, Double bz) {
		RegionManager.addRegion(region, world, ax, ay, az, bx, by, bz);
	}
	
	public static void addRegion(String region, Location A, Location B) {
		RegionManager.addRegion(region, A, B);
	}
	
	public static void deleteRegion(String region)  {
		RegionManager.deleteRegion(region);
	}
	
	public static void setPointA(String name, Location l) {
		RegionManager.setPointA(name, l);
	}
	
	public static void setPointB(String name, Location l) {
		RegionManager.setPointB(name, l);
	}
	
	public static void changeStatus(String region) {
		RegionManager.changeStatus(region);
	}
	
	public static boolean isActive(String name) {
		return RegionManager.isActive(name);
	}
	
	public static List<String> getRegionList() {
		return RegionManager.getRegionList();
	}
	
	public static boolean isRegion3D(Location l) {
		return RegionManager.isRegion3D(l);
	}
	
	public static boolean isRegion2D(Location l) {
		return RegionManager.isRegion3D(l);
	}
	
	public static List<String> getRegionsInRange(Block l, int range) {
		return RegionManager.getRegionsInRange(l, range);
	}

	public static double getDistanceBetween3DPoints( double aX, double aY, double aZ, double bX, double bY, double bZ) {
		return RegionManager.getDistanceBetween3DPoints(aX, aY, aZ, bX, bY, bZ);
	}

	public static double getDistanceBetween2DPoints( double aX, double aY, double bX, double bY) {
		return RegionManager.getDistanceBetween2DPoints(aX, aY, bX, bY);
	}
	
}
