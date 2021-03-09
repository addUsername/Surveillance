package com.addusername.surv.dtos;

import java.util.List;

public class HomeDTO {

    private List<HomePiDTO> pi_ids;

    public HomeDTO() {}
    public HomeDTO(List<HomePiDTO> pi_ids) {
        this.pi_ids = pi_ids;
    }

    public List<HomePiDTO> getPi_ids() {
        return pi_ids;
    }

    public void setPi_ids(List<HomePiDTO> pi_ids) {
        this.pi_ids = pi_ids;
    }
}
