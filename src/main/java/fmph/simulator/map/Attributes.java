
package fmph.simulator.map;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "rot_cx",
    "rot_cy",
    "rot_radius",
    "angle"
})
public class Attributes {

    @JsonProperty("rot_cx")
    private Integer rotCx;
    @JsonProperty("rot_cy")
    private Integer rotCy;
    @JsonProperty("rot_radius")
    private Double rotRadius;
    @JsonProperty("angle")
    private Double angle;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("rot_cx")
    public Integer getRotCx() {
        return rotCx;
    }

    @JsonProperty("rot_cx")
    public void setRotCx(Integer rotCx) {
        this.rotCx = rotCx;
    }

    @JsonProperty("rot_cy")
    public Integer getRotCy() {
        return rotCy;
    }

    @JsonProperty("rot_cy")
    public void setRotCy(Integer rotCy) {
        this.rotCy = rotCy;
    }

    @JsonProperty("rot_radius")
    public Double getRotRadius() {
        return rotRadius;
    }

    @JsonProperty("rot_radius")
    public void setRotRadius(Double rotRadius) {
        this.rotRadius = rotRadius;
    }

    @JsonProperty("angle")
    public Double getAngle() {
        return angle;
    }

    @JsonProperty("angle")
    public void setAngle(Double angle) {
        this.angle = angle;
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
