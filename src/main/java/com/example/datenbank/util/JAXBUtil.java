package com.example.datenbank.util;

import com.example.datenbank.model.Region;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;

public class JAXBUtil {
    public static String toXml(ObservableList<Region> regions) throws JAXBException {
        List<Region> simpleList = regions.stream().collect(Collectors.toList());
        JAXBContext jaxbContext = JAXBContext.newInstance(RegionWrapper.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter sw = new StringWriter();
        RegionWrapper wrapper = new RegionWrapper();
        wrapper.setRegions(simpleList);
        marshaller.marshal(wrapper, sw);
        return sw.toString();
    }
}
