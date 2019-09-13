package me.pablockda.wr.api;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

import me.pablockda.wr.managers.RegionManager;


public final class WorldRegionAPI {
	
	public static final void addRegion(String region, String world) {
		RegionManager.addRegion(region, world);
	}
	
	public static final void addRegion(String region, String world, Double ax, Double ay, Double az, Double bx, Double by, Double bz) {
		RegionManager.addRegion(region, world, ax, ay, az, bx, by, bz);
	}
	
	public static final void addRegion(String region, Location A, Location B) {
		RegionManager.addRegion(region, A, B);
	}
	
	public static final void deleteRegion(String region)  {
		RegionManager.deleteRegion(region);
	}
	
	public static final void setPointA(String name, Location l) {
		RegionManager.setPointA(name, l);
	}
	
	public static final void setPointB(String name, Location l) {
		RegionManager.setPointB(name, l);
	}
	
	public static final void changeStatus(String region) {
		RegionManager.changeStatus(region);
	}
	
	public static final boolean isActive(String name) {
		return RegionManager.isActive(name);
	}
	
	public static final List<String> getRegionList() {
		return RegionManager.getRegionList();
	}
	
	public static final boolean isRegion3D(Location l) {
		return RegionManager.isRegion3D(l);
	}
	
	public static final boolean isRegion2D(Location l) {
		return RegionManager.isRegion3D(l);
	}
	
	public static List<String> getRegionsInRange(Block l, int range) {
		return RegionManager.getRegionsInRange(l, range);
	}
	
}
