
package fmph.simulator.map;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "x",
    "y",
    "heading"
})
public class EndPose {

    @JsonProperty("x")
    private Integer x;
    @JsonProperty("y")
    private Double y;
    @JsonProperty("heading")
    private Double heading;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("x")
    public Integer getX() {
        return x;
    }

    @JsonProperty("x")
    public void setX(Integer x) {
        this.x = x;
    }

    @JsonProperty("y")
    public Double getY() {
        return y;
    }

    @JsonProperty("y")
    public void setY(Double y) {
        this.y = y;
    }

    @JsonProperty("heading")
    public Double getHeading() {
        return heading;
    }

    @JsonProperty("heading")
    public void setHeading(Double heading) {
        this.heading = heading;
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
