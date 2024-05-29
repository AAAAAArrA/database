package com.example.datenbank.util;

import com.example.datenbank.model.UnwetterArt;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "UnwetterArts")
public class UnwetterartWrapper {
    private List<UnwetterArt> unwetterArts;

    @XmlElement(name = "UnwetterArt")
    public List<UnwetterArt> getUnwetterArts() {
        return unwetterArts;
    }

    public void setUnwetterArts(List<UnwetterArt> unwetterArts) {
        this.unwetterArts = unwetterArts;
    }
}
