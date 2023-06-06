package de.haw_hamburg.smartclassroom;

public interface BrightnessObserver extends MqttObserver {

    public void onBrightnessChanged(double brightness);
}
