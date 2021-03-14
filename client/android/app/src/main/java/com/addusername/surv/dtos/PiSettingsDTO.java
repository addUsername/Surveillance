package com.addusername.surv.dtos;

import java.util.List;

public class PiSettingsDTO {
    private Long id; // RPI ID NOT SETTIGS ID
    // video
    private Boolean saveVideo = null;
    private String videoExt = null;
    private String videoRes = null;
    private String bitrate = null;

    private String framerate=null;
    private String rotation=null;
    private String timeRecording=null;

    // detection
    private String model=null;
    private String weights=null;
    private List<String> classes=null;
    private String threshold=null;

    public PiSettingsDTO(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSaveVideo() {
        return saveVideo;
    }

    public void setSaveVideo(Boolean saveVideo) {
        this.saveVideo = saveVideo;
    }

    public String getVideoExt() {
        return videoExt;
    }

    public void setVideoExt(String videoExt) {
        this.videoExt = videoExt;
    }

    public String getVideoRes() {
        return videoRes;
    }

    public void setVideoRes(String videoRes) {
        this.videoRes = videoRes;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getFramerate() {
        return framerate;
    }

    public void setFramerate(String framerate) {
        this.framerate = framerate;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    public String getTimeRecording() {
        return timeRecording;
    }

    public void setTimeRecording(String timeRecording) {
        this.timeRecording = timeRecording;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getWeights() {
        return weights;
    }

    public void setWeights(String weights) {
        this.weights = weights;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }
}
