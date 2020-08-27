package creative.dru.org;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.World;
import org.bukkit.util.Vector;

public class FileManager {
	static HashMap<String,List<Vector>> locs = new HashMap<String,List<Vector>>();
	public static void read(World w) {
		BufferedReader reader = null;
		List<Vector> ls = new ArrayList<Vector>();
		if(new File(w.getWorldFolder().getPath()+"/creativeAir").exists()) {
			try {
				  reader = new BufferedReader(new InputStreamReader(new FileInputStream(w.getWorldFolder().getPath()+"/creativeAir"), "UTF-8"));
				  String str = null;
				  while ((str = reader.readLine()) != null) {
					String[] loc = str.split("_");
					ls.add(new Vector(Integer.parseInt(loc[0]),Integer.parseInt(loc[1]),Integer.parseInt(loc[2])));
				  }
				  
				} catch (FileNotFoundException e) {
				  e.printStackTrace();
				} catch (IOException e) {
				  e.printStackTrace();
				} finally {
				  try {
				    reader.close();
				  } catch (IOException e) {
				    e.printStackTrace();
				  }
				}			
		}

		  if(locs.containsKey(w.getName())) 
			 locs.remove(w.getName());
		  locs.put(w.getName(), ls);

	}
	
	public static void write(World w) {
		if(!locs.containsKey(w.getName())) read(w);
		for(Vector v: locs.get(w.getName())) {
			BufferedWriter fw = null;
			try {
				File file = new File(w.getWorldFolder().getPath()+"/creativeAir");
				if(file.exists()) file.delete();
				file.createNewFile();
				fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
				fw.append(v.getBlockX()+"_"+v.getBlockY()+"_"+v.getBlockZ());
				fw.newLine();
				fw.flush(); // 全部寫入緩存中的內容
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}	
		}
		
	}
	
	public static void write(World w,Vector v) {
		BufferedWriter fw = null;
		try {
			if(!locs.containsKey(w.getName())) read(w);

			locs.get(w.getName()).add(v);
			File file = new File(w.getWorldFolder().getPath()+"/creativeAir");
			file.createNewFile();
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
			fw.append(v.getBlockX()+"_"+v.getBlockY()+"_"+v.getBlockZ());
			fw.newLine();
			fw.flush(); // 全部寫入緩存中的內容
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
