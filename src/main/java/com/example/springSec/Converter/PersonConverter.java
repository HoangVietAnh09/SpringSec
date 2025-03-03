package com.example.springSec.Converter;

import com.example.springSec.Entity.Person;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PersonConverter implements Converter {
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        Person person = (Person) o;
        hierarchicalStreamWriter.startNode("firstName");
        hierarchicalStreamWriter.setValue(person.getFirstName());
        hierarchicalStreamWriter.endNode();
        hierarchicalStreamWriter.startNode("lastName");
        hierarchicalStreamWriter.setValue(person.getLastName());
        hierarchicalStreamWriter.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        Person person = new Person();
        hierarchicalStreamReader.moveDown();
        person.setFirstName(hierarchicalStreamReader.getValue());
        person.setLastName(hierarchicalStreamReader.getValue());
        hierarchicalStreamReader.moveUp();
        return person;
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(Person.class);
    }
}
