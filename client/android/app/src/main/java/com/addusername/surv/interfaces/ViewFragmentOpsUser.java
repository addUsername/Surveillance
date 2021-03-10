package com.addusername.surv.interfaces;

import java.util.List;

public interface ViewFragmentOpsUser {
    void addRpi(String alias, String location);
    void loadImgs(List<Integer> raspberryIds);
}
