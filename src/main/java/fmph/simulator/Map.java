package fmph.simulator;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fmph.simulator.map.LaserTag;
import fmph.simulator.map.MapSchema;
import fmph.simulator.map.Segment;
import fmph.simulator.vizualization.component.Function;
import fmph.simulator.vizualization.component.IdLocation;

public class Map {
	
	//static final String mapPath = "target/F1-114-oval-only.json";
    static final String mapPath = "target/F1-114-current.json"; //todo real loader
	
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
		loadIdentificator();
	}
	void loadIdentificator(){
		for(Segment segment : map.getSegments()){

			List<LaserTag> tags = segment.getLaserTags();
			IdLocation idloc = new IdLocation(segment);

			for (int i = 0; i < tags.size(); i++) {
				if(tags.get(i) == null){
					continue;
				}

				idloc.identifier_location(i, segment);


				//tags.get(i).setX(Function.tx(idloc.getPos()[0]));
				//tags.get(i).setY(Function.ty(idloc.getPos()[1]));
				tags.get(i).setX(idloc.getPos()[0]);
				tags.get(i).setY(idloc.getPos()[1]);

				tags.get(i).setGamma(Math.toDegrees(idloc.getGamma()));
			}

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
