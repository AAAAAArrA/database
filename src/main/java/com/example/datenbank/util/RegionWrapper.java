package com.example.datenbank.util;

import com.example.datenbank.model.Region;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Regions")
public class RegionWrapper {
    private List<Region> regions;

    @XmlElement(name = "Region")
    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }
}

