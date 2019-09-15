
package fmph.simulator.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "format_version",
    "map_name",
    "segments"
})
public class MapSchema {

    @JsonProperty("format_version")
    private String formatVersion;
    @JsonProperty("map_name")
    private String mapName;
    @JsonProperty("segments")
    private List<Segment> segments = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("format_version")
    public String getFormatVersion() {
        return formatVersion;
    }

    @JsonProperty("format_version")
    public void setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
    }

    @JsonProperty("map_name")
    public String getMapName() {
        return mapName;
    }

    @JsonProperty("map_name")
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    @JsonProperty("segments")
    public List<Segment> getSegments() {
        return segments;
    }

    @JsonProperty("segments")
    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
