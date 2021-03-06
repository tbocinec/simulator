
package fmph.simulator.map;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "unique_id",
    "type",
    "relative_location",
    "external",
    "offset",
    "optional",
    "disabled"
})
public class LaserTag {

    @JsonProperty("unique_id")
    private Integer uniqueId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("relative_location")
    private Double relativeLocation;
    @JsonProperty("external")
    private Boolean external;
    @JsonProperty("offset")
    private Double offset;
    @JsonProperty("optional")
    private Boolean optional;
    @JsonProperty("disabled")
    private Boolean disabled;    
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    private double x;
    @JsonIgnore
    private double y;
    @JsonIgnore
    private double gamma;


    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @JsonProperty("unique_id")
    public Integer getUniqueId() {
        return uniqueId;
    }

    @JsonProperty("unique_id")
    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("relative_location")
    public Double getRelativeLocation() {
        return relativeLocation;
    }

    @JsonProperty("relative_location")
    public void setRelativeLocation(Double relativeLocation) {
        this.relativeLocation = relativeLocation;
    }

    @JsonProperty("external")
    public Boolean getExternal() {
        return external;
    }

    @JsonProperty("external")
    public void setExternal(Boolean external) {
        this.external = external;
    }
    
    @JsonProperty("disabled")
    public Boolean getDisabled() {
        return disabled;
    }

    @JsonProperty("disabled")
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    
    @JsonProperty("offset")
    public Double getOffset() {
        return offset;
    }

    @JsonProperty("offset")
    public void setOffset(Double offset) {
        this.offset = offset;
    }

    @JsonProperty("optional")
    public Boolean getOptional() {
        return optional;
    }

    @JsonProperty("optional")
    public void setOptional(Boolean optional) {
        this.optional = optional;
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
