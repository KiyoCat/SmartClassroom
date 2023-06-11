package de.haw_hamburg.smartclassroom.Model;

import de.haw_hamburg.smartclassroom.ViewModel.MqttObserver;

public interface BrightnessObserver extends MqttObserver {

    public void onBrightnessChanged(double brightness);
}
