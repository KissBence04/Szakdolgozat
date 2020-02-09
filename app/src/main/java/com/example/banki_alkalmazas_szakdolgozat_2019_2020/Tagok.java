package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import java.util.Random;

public class Tagok {
    public String felhasznalonev,email,jelszo,kartyaszam;
    public int egyenleg;

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

    public String getKartyaszam() {
        return kartyaszam;
    }

    public void setKartyaszam() {
        Random r=new Random();
            String kartyaszam1 = Integer.toString((r.nextInt(10000)+1));
            String kartyaszam2 = Integer.toString((r.nextInt(10000)+1));
            String kartyaszam3 = Integer.toString((r.nextInt(10000)+1));
            String kartyaszam4 = Integer.toString((r.nextInt(10000)+1));
            this.kartyaszam = kartyaszam1 + " " + kartyaszam2 + " " + kartyaszam3 + " " + kartyaszam4;
    }

    public Integer getEgyenleg() {
        return egyenleg;
    }

    public void setEgyenleg() {
        this.egyenleg = 3500;
    }
}
