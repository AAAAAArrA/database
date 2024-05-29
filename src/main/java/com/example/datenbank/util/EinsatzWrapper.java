package com.example.datenbank.util;

import com.example.datenbank.model.Einsatz;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "EinsatzList")
public class EinsatzWrapper {
    private List<Einsatz> einsatzList;

    @XmlElement(name = "Einsatz")
    public List<Einsatz> getEinsatzList() {
        return einsatzList;
    }

    public void setEinsatzList(List<Einsatz> einsatzList) {
        this.einsatzList = einsatzList;
    }
}
