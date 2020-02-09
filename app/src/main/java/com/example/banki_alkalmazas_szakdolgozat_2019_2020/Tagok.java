package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import java.util.Random;

public class Tagok {
    public String felhasznalonev,email,jelszo;
    public int kartyaszam,egyenleg;

    public String getFelhasznalonev() {
        return felhasznalonev;
    }

    public void setFelhasznalonev(String felhasznalonev) {
        this.felhasznalonev = felhasznalonev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }

    public Integer getKartyaszam() {
        return kartyaszam;
    }

    public void setKartyaszam() {
        Random r=new Random();
        int kartyaszam = r.nextInt(10);
        for (int i=0;i<=12;i++) {

        }
        this.kartyaszam = kartyaszam;
    }

    public Integer getEgyenleg() {
        return egyenleg;
    }

    public void setEgyenleg() {
        this.egyenleg = 3500;
    }
}
