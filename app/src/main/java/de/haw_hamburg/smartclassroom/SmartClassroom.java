package de.haw_hamburg.smartclassroom;

public class SmartClassroom {

    private double temperature;
    private double brightness;

    public void setTemperature(double temperature){
        this.temperature = temperature;
    }
    public void setBrightness(double brightness){
        this.brightness = brightness;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getBrightness() {
        return brightness;
    }
}

