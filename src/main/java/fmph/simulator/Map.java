package fmph.simulator;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fmph.simulator.map.MapSchema;

public class Map {
	
	static final String mapPath = "target/F1-114-current.json";
	
	private MapSchema map;	
	
	public void loadMap() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			map = objectMapper.readValue(new File(mapPath), MapSchema.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Chyba nacitania suboru mapy");
			System.exit(1);
			//e.printStackTrace();
		}
	}
	
	public String getMapName() {
		return map.getMapName();
	}
	
	
	public MapSchema getMap() {
		return map;
	}

	public void setMap(MapSchema map) {
		this.map = map;
	}


}
