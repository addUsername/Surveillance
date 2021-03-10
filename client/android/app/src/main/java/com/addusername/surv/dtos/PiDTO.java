package com.addusername.surv.dtos;

public class PiDTO {
    public String alias;
    public String location;

    public PiDTO(){}
    public PiDTO(String alias, String location) {
        this.alias = alias;
        this.location = location;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
