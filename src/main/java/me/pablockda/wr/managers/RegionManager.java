package me.pablockda.wr.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

import me.pablockda.wr.WorldRegion;
import me.pablockda.wr.objects.WR;
import me.pablockda.wr.objects.WR.Status;
import me.pablockda.wr.utils.Config;
import me.pablockda.wr.utils.Mensajes;

public class RegionManager {

	private static HashMap<String, WR> regions;
	
	private static Config config = WorldRegion.instance.getConfig();
	
	public RegionManager() {
		regions = new HashMap<String, WR>();
		loadArenas();
	}
	
	
	private void loadArenas()  {

	    try  {
			if( config.contains("Regions.") ) {
				
				for(String key : config.getConfigurationSection("Regions.").getKeys(false)){
					
					Status s = config.getString("Regions." + key + ".Status").equals("OFF") ? Status.OFF : Status.ON;
					String world =  config.getString("Regions." + key + ".World") ;
					
					String A[] = config.getString("Regions." + key + ".A").split(",");
					
					Double ax = A[0].equals("null") ? null : Double.valueOf(A[0]) ;
					Double ay = A[1].equals("null") ? null : Double.valueOf(A[1]) ;
					Double az = A[1].equals("null") ? null : Double.valueOf(A[2]) ;
					
					String B[] = config.getString("Regions." + key + ".B").split(","); 
					
					Double bx = B[0].equals("null") ? null : Double.valueOf(B[0]) ;
					Double by = B[1].equals("null") ? null : Double.valueOf(B[1]) ;
					Double bz = B[1].equals("null") ? null : Double.valueOf(B[2]) ;
					
					regions.put( key, new WR( s, world, ax, ay, az, bx, by, bz ) );
				}
			}			
			Bukkit.getConsoleSender().sendMessage(Mensajes.ChatOnColor("&4[&6WorldRegion&4] &8>> &eRegions loaded correctly"));
			
	    } catch (Exception e) {
	    	Bukkit.getConsoleSender().sendMessage(Mensajes.ChatOnColor("&4[&6WorldRegion&4] &8>> &4Error: &eLoading regions"));
		    e.printStackTrace();
	    }
	      
    }
	

	
	public static void addRegion(String region, String world) {
		
		if( regions.containsKey(region) ) {
			return;
		}
		
		regions.put( region, new WR( world ) );
		
		config.set("Regions."+region + ".Status", Status.OFF.toString() );
		config.set("Regions."+region + ".World", world );
		config.set("Regions."+region + ".A", "null,null" );
		config.set("Regions."+region + ".B", "null,null" );
		config.save();
		
	}
	
	
	
	public static void addRegion(String region, String world, Double ax, Double ay, Double az, Double bx, Double by, Double bz) {
		
		if( regions.containsKey(region) ) {
			return;
		}
		
		regions.put( region, new WR( world, ax, ay, az, bx, by, bz ) );
		config.set("Regions."+region + ".Status", Status.ON.toString() );
		config.set("Regions."+region + ".World", world );
		config.set("Regions."+region + ".A", Math.floor(ax) + "," + Math.floor(ay) + "," + Math.floor(az)  );
		config.set("Regions."+region + ".B", Math.floor(bx) + "," + Math.floor(by) + "," + Math.floor(bz)  );
		config.save();
		
	}
	
	
	
	public static void addRegion(String region, Location A, Location B) {
		
		if( regions.containsKey(region) ) {
			return;
		}
		
		if(!A.getWorld().getName().equals(B.getWorld().getName())) {
			return;
		}
		
		regions.put( region, new WR( A.getWorld().getName(), A.getX(), A.getY(), A.getZ(), B.getX(), B.getY(), B.getZ() ) );
		config.set("Regions."+region + ".Status", Status.ON.toString() );
		config.set("Regions."+region + ".World", A.getWorld().getName() );
		config.set("Regions."+region + ".A", Math.floor(A.getX()) + "," + Math.floor(A.getY()) + "," + Math.floor(A.getZ())  );
		config.set("Regions."+region + ".B", Math.floor(B.getX()) + "," + Math.floor(B.getY()) + "," + Math.floor(B.getZ())  );
		config.save();
		
	}
	
	
	
	
	public static void deleteRegion(String region) {
		
		if(!regions.containsKey(region)) {
			return;
		}
		
		regions.remove(region);
		config.set("Regions."+region, null);
		config.save();
	}
	
	
	
	public static void setPointA(String name, Location l) {
		
		if(!regions.containsKey(name)) {
			return;
		}
		
		WR region = regions.get(name);
		
		if( !l.getWorld().getName().equals(region.getWorld()) ) {
			return;
		}

			region.setX1( l.getX() );
			region.setY1( l.getY() );
			region.setZ1( l.getZ() );
			
			config.set("Regions."+name + ".A", Math.floor(l.getX()) + "," + Math.floor(l.getY()) + "," + Math.floor(l.getZ())  );
			config.save();
		
		if( (Double)region.getX1()!=null && (Double)region.getX2()!=null ) {
			region.setStatus(Status.ON);
		}
		
	}
	
	
	public static void setPointB(String name, Location l) {
		
		if(!regions.containsKey(name)) {
			return;
		}
		
		WR region = regions.get(name);
		
		if( !l.getWorld().getName().equals(region.getWorld()) ) {
			return;
		}

			region.setX2( l.getX() );
			region.setY2( l.getY() );
			region.setZ2( l.getZ() );
			
			config.set("Regions."+name + ".B", Math.floor(l.getX()) +  "," + Math.floor(l.getY()) + "," + Math.floor(l.getZ())  );
			config.save();
		
		if( (Double)region.getX1()!=null && (Double)region.getX2()!=null ) {
			region.setStatus(Status.ON);
		}
		
	}
	
	
	public static void changeStatus(String region) {
		
		if(!regions.containsKey(region)) {
			return;
		}
		
		WR object = regions.get(region);
		
		if( (Double)object.getX1() == null || (Double)object.getX2() == null ) {
			return;
		}
		
		if(object.getStatus() == Status.OFF) {
			object.setStatus(Status.ON);
			config.set("Regions."+region + ".Status", "ON" );
		}else {
			object.setStatus(Status.OFF);
			config.set("Regions."+region + ".Status", "OFF" );
		}
		config.save();
		
	}
	
	
	public static boolean isActive(String name) {
		
		if(!regions.containsKey(name)) {
			return false;
		}
		
		boolean sino = regions.get(name).getStatus() == Status.ON ? true : false;
		return sino;
	}
	
	
	public static List<String> getRegionList() {
		
		if(regions.isEmpty()) { return null; }
		
		List<String> list = new ArrayList<String>() ;
		
		for (  Map.Entry<String, WR> WR : regions.entrySet()) {
			list.add(WR.getKey());
		}
		
		return list;
	}
	
	
	public static boolean isRegion3D(Location l) {
		
		if( regions.isEmpty() ) { return true; }
		
		boolean check = true;
		String world = l.getWorld().getName();
		
		for (  Map.Entry<String, WR> WG : regions.entrySet()) {
			
			if( WG.getValue().getStatus() == Status.ON && WG.getValue().getWorld().equals(world) ) {
				
				check = false;
				
				double[] dim = new double[2];
				dim[0] = WG.getValue().getX1();
			    dim[1] = WG.getValue().getX2();
			    Arrays.sort(dim);
			    
				double[] dim2 = new double[2];
				dim2[0] = WG.getValue().getY1();
			    dim2[1] = WG.getValue().getY2();
			    Arrays.sort(dim2);
			    
			    double[] dim3 = new double[2];
				dim3[0] = WG.getValue().getZ1();
			    dim3[1] = WG.getValue().getZ2();
			    Arrays.sort(dim3);
				
			    if(  l.getX() >= dim[0] && l.getX() <= dim[1]  && l.getY() >= dim2[0] && l.getY() <= dim2[1]  &&  l.getZ() >= dim3[0] && l.getZ() <= dim3[1] ) {
			    	return true;
			    }
			}else if( WG.getValue().getStatus() == Status.ON  ) {
				check = false;
			}
		}
		
		 return check;
	}
	
	
	
	public static boolean isRegion2D(Location l) {
		
		if( regions.isEmpty() ) { return true; }
		
		boolean check = true;
		String world = l.getWorld().getName();
		
		for (  Map.Entry<String, WR> WG : regions.entrySet()) {
			
			if( WG.getValue().getStatus() == Status.ON && WG.getValue().getWorld().equals(world) ) {
				
				check = false;
				
				double[] dim = new double[2];
				dim[0] = WG.getValue().getX1();
			    dim[1] = WG.getValue().getX2();
			    Arrays.sort(dim);
			    
			    double[] dim3 = new double[2];
				dim3[0] = WG.getValue().getZ1();
			    dim3[1] = WG.getValue().getZ2();
			    Arrays.sort(dim3);
				
			    if(  l.getX() >= dim[0] && l.getX() <= dim[1]  && l.getZ() >= dim3[0] && l.getZ() <= dim3[1] ) {
			    	return true;
			    }
			}else if( WG.getValue().getStatus() == Status.ON  ) {
				check = false;
			}
		}
		return check;
	}
	
	
	public static List<String> getRegionsInRange(Block l, int range) {
		
		List<String> list = new ArrayList<String>();
		
		if(!Mensajes.isNumericPositiveOnly( String.valueOf(range) )) { return list;	}
		
		if( regions.isEmpty() ) { return list; }
		
		String world = l.getWorld().getName();
		
		double minX=  l.getX() + range;
		double maxX =  l.getX() - range;
		
		double minZ=  l.getZ() + range;
		double maxZ =  l.getZ() - range;
		
		for (  Map.Entry<String, WR> WG : regions.entrySet()) {
			
			if( WG.getValue().getWorld().equals(world) ) {
				
				double[] dim = new double[2];
				dim[0] = WG.getValue().getX1();
			    dim[1] = WG.getValue().getX2();
			    Arrays.sort(dim);
			    
			    double[] dim3 = new double[2];
				dim3[0] = WG.getValue().getZ1();
			    dim3[1] = WG.getValue().getZ2();
			    Arrays.sort(dim3);	
			    
			    if( dim[0] >= maxX && dim[0] <= minX &&
			    	dim[1] >= maxX && dim[1] <= minX &&
			    	dim3[0] >= maxZ && dim3[0] <= minZ &&
			    	dim3[1] >= maxZ && dim3[1] <= minZ   ) {
			    	list.add(WG.getKey());
			    }
			}
		}
		
		return list;
	}
	
	
	
	
	
}
