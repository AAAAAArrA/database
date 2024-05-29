package com.example.datenbank.util;


import com.example.datenbank.model.Ereignis;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "EreignisList")
public class EreignisWrapper {
    private List<Ereignis> ereignisList;

    @XmlElement(name = "Ereignis")
    public List<Ereignis> getEreignisList(){ return ereignisList;}

    public void setEreignisList(List<Ereignis> ereignisList){
        this.ereignisList = ereignisList;
    }
}
