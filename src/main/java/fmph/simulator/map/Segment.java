
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
    "segment_id",
    "description",
    "start_pose",
    "end_pose",
    "length",
    "previous_segments",
    "next_segments",
    "segment_shape",
    "segment_width",
    "segment_height",
    "driving_surface",
    "max_driving_speed",
    "min_driving_speed",
    "recommended_entry_speed",
    "laser_tags"
})
public class Segment {

    @JsonProperty("segment_id")
    private String segmentId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("start_pose")
    private StartPose startPose;
    @JsonProperty("end_pose")
    private EndPose endPose;
    @JsonProperty("length")
    private Double length;
    @JsonProperty("previous_segments")
    private List<String> previousSegments = null;
    @JsonProperty("next_segments")
    private List<String> nextSegments = null;
    @JsonProperty("segment_shape")
    private SegmentShape segmentShape;
    @JsonProperty("segment_width")
    private Double segmentWidth;
    @JsonProperty("segment_height")
    private Integer segmentHeight;
    @JsonProperty("driving_surface")
    private DrivingSurface drivingSurface;
    @JsonProperty("max_driving_speed")
    private Integer maxDrivingSpeed;
    @JsonProperty("min_driving_speed")
    private Double minDrivingSpeed;
    @JsonProperty("recommended_entry_speed")
    private Double recommendedEntrySpeed;
    @JsonProperty("laser_tags")
    private List<LaserTag> laserTags = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("segment_id")
    public String getSegmentId() {
        return segmentId;
    }

    @JsonProperty("segment_id")
    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("start_pose")
    public StartPose getStartPose() {
        return startPose;
    }

    @JsonProperty("start_pose")
    public void setStartPose(StartPose startPose) {
        this.startPose = startPose;
    }

    @JsonProperty("end_pose")
    public EndPose getEndPose() {
        return endPose;
    }

    @JsonProperty("end_pose")
    public void setEndPose(EndPose endPose) {
        this.endPose = endPose;
    }

    @JsonProperty("length")
    public Double getLength() {
        return length;
    }

    @JsonProperty("length")
    public void setLength(Double length) {
        this.length = length;
    }

    @JsonProperty("previous_segments")
    public List<String> getPreviousSegments() {
        return previousSegments;
    }

    @JsonProperty("previous_segments")
    public void setPreviousSegments(List<String> previousSegments) {
        this.previousSegments = previousSegments;
    }

    @JsonProperty("next_segments")
    public List<String> getNextSegments() {
        return nextSegments;
    }

    @JsonProperty("next_segments")
    public void setNextSegments(List<String> nextSegments) {
        this.nextSegments = nextSegments;
    }

    @JsonProperty("segment_shape")
    public SegmentShape getSegmentShape() {
        return segmentShape;
    }

    @JsonProperty("segment_shape")
    public void setSegmentShape(SegmentShape segmentShape) {
        this.segmentShape = segmentShape;
    }

    @JsonProperty("segment_width")
    public Double getSegmentWidth() {
        return segmentWidth;
    }

    @JsonProperty("segment_width")
    public void setSegmentWidth(Double segmentWidth) {
        this.segmentWidth = segmentWidth;
    }

    @JsonProperty("segment_height")
    public Integer getSegmentHeight() {
        return segmentHeight;
    }

    @JsonProperty("segment_height")
    public void setSegmentHeight(Integer segmentHeight) {
        this.segmentHeight = segmentHeight;
    }

    @JsonProperty("driving_surface")
    public DrivingSurface getDrivingSurface() {
        return drivingSurface;
    }

    @JsonProperty("driving_surface")
    public void setDrivingSurface(DrivingSurface drivingSurface) {
        this.drivingSurface = drivingSurface;
    }

    @JsonProperty("max_driving_speed")
    public Integer getMaxDrivingSpeed() {
        return maxDrivingSpeed;
    }

    @JsonProperty("max_driving_speed")
    public void setMaxDrivingSpeed(Integer maxDrivingSpeed) {
        this.maxDrivingSpeed = maxDrivingSpeed;
    }

    @JsonProperty("min_driving_speed")
    public Double getMinDrivingSpeed() {
        return minDrivingSpeed;
    }

    @JsonProperty("min_driving_speed")
    public void setMinDrivingSpeed(Double minDrivingSpeed) {
        this.minDrivingSpeed = minDrivingSpeed;
    }

    @JsonProperty("recommended_entry_speed")
    public Double getRecommendedEntrySpeed() {
        return recommendedEntrySpeed;
    }

    @JsonProperty("recommended_entry_speed")
    public void setRecommendedEntrySpeed(Double recommendedEntrySpeed) {
        this.recommendedEntrySpeed = recommendedEntrySpeed;
    }

    @JsonProperty("laser_tags")
    public List<LaserTag> getLaserTags() {
        return laserTags;
    }

    @JsonProperty("laser_tags")
    public void setLaserTags(List<LaserTag> laserTags) {
        this.laserTags = laserTags;
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
