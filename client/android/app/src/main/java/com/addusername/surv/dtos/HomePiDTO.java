package com.addusername.surv.dtos;

public class HomePiDTO {

    private Long id;
    private String status;
    private String alias;

    public HomePiDTO() {
    }

    public HomePiDTO(Long id, String status, String alias) {
        this.id = id;
        this.status = status;
        this.alias = alias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
