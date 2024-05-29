package com.example.datenbank.util;

import com.example.datenbank.model.Schaden;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "SchadenList")
public class SchadenWrapper {
    private List<Schaden> schadenList;

    @XmlElement(name = "Schaden")
    public List<Schaden> getSchadenList() {
        return schadenList;
    }

    public void setSchadenList(List<Schaden> schadenList) {
        this.schadenList = schadenList;
    }
}

