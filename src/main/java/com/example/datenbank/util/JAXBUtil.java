package com.example.datenbank.util;

import com.example.datenbank.model.*;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

public class JAXBUtil {
    public static String toXml(ObservableList<?> list) throws JAXBException {
        if (list == null || list.isEmpty()) {
            throw new JAXBException("The list is empty or null.");
        }

        Object firstItem = list.get(0);
        Class<?> clazz = firstItem.getClass();
        JAXBContext context;
        Object wrapper;

        if (firstItem instanceof Region) {
            context = JAXBContext.newInstance(RegionWrapper.class);
            wrapper = new RegionWrapper();
            ((RegionWrapper) wrapper).setRegions(list.stream().map(item -> (Region) item).collect(Collectors.toList()));
        } else if (firstItem instanceof Organisation) {
            context = JAXBContext.newInstance(OrganisationWrapper.class);
            wrapper = new OrganisationWrapper();
            ((OrganisationWrapper) wrapper).setOrganisations(list.stream().map(item -> (Organisation) item).collect(Collectors.toList()));
        } else if (firstItem instanceof Schaden) {
            context = JAXBContext.newInstance(SchadenWrapper.class);
            wrapper = new SchadenWrapper();
            ((SchadenWrapper) wrapper).setSchadenList(list.stream().map(item -> (Schaden) item).collect(Collectors.toList()));
        } else if (firstItem instanceof Einsatz) {
            context = JAXBContext.newInstance(EinsatzWrapper.class);
            wrapper = new EinsatzWrapper();
            ((EinsatzWrapper) wrapper).setEinsatzList(list.stream().map(item -> (Einsatz) item).collect(Collectors.toList()));
        } else if (firstItem instanceof UnwetterArt) {
            context = JAXBContext.newInstance(UnwetterartWrapper.class);
            wrapper = new UnwetterartWrapper();
            ((UnwetterartWrapper) wrapper).setUnwetterArts(list.stream().map(item -> (UnwetterArt) item).collect(Collectors.toList()));
        } else if (firstItem instanceof Ereignis) {
            context = JAXBContext.newInstance(EreignisWrapper.class);
            wrapper = new EreignisWrapper();
            ((EreignisWrapper) wrapper).setEreignisList(list.stream().map(item -> (Ereignis) item).collect(Collectors.toList()));
        } else {
            throw new JAXBException("Unsupported type: " + clazz.getName());
        }

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter sw = new StringWriter();
        marshaller.marshal(wrapper, sw);
        return sw.toString();
    }
}
