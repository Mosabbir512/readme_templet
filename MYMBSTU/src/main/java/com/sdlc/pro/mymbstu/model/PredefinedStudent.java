package com.sdlc.pro.mymbstu.model;

import jakarta.persistence.Entity;


import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity



public class PredefinedStudent {




    @Id
    private String idPre;
    private String departmentPre;
    private String sessionPre;

    private String hallPre;

    public PredefinedStudent(String idPre, String departmentPre, String sessionPre, String hallPre) {
        this.idPre = idPre;
        this.departmentPre = departmentPre;
        this.sessionPre = sessionPre;
        this.hallPre = hallPre;
    }

    public PredefinedStudent() {
    }

    public String getIdPre() {
        return idPre;
    }

    public void setIdPre(String idPre) {
        this.idPre = idPre;
    }

    public String getDepartmentPre() {
        return departmentPre;
    }

    public void setDepartmentPre(String departmentPre) {
        this.departmentPre = departmentPre;
    }

    public String getSessionPre() {
        return sessionPre;
    }

    public void setSessionPre(String sessionPre) {
        this.sessionPre = sessionPre;
    }


    public String getHallPre() {
        return hallPre;
    }

    public void setHallPre(String hallPre) {
        this.hallPre = hallPre;
    }
}
