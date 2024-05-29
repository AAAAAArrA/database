package com.example.datenbank.util;

import com.example.datenbank.model.Organisation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Organisations")
public class OrganisationWrapper {
    private List<Organisation> organisations;

    @XmlElement(name = "Organisation")
    public List<Organisation> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(List<Organisation> organisations) {
        this.organisations = organisations;
    }
}
