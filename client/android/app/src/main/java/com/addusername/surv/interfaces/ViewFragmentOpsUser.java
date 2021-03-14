package com.addusername.surv.interfaces;

import com.addusername.surv.dtos.PiSettingsDTO;

import java.util.List;

public interface ViewFragmentOpsUser {
    void addRpi(String alias, String location);
    void loadImgs(List<Integer> raspberryIds);
    void handleHomeClickEvent(int RpiId, int actionId);
    void getPiSettings(int id);
    void submitSettings(PiSettingsDTO updateSettings);
}
