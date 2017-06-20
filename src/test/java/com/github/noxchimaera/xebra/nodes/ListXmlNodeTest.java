/*
 * Copyright 2016 Max Balushkin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.noxchimaera.xebra.nodes;

import com.github.noxchimaera.xebra.Person;
import com.github.noxchimaera.xebra.Phone;
import com.github.noxchimaera.xebra.nodes.readers.ListXmlNodeReader;
import com.github.noxchimaera.xebra.nodes.readers.ReadStrategy;
import com.github.noxchimaera.xebra.nodes.readers.SimpleXmlNodeReader;
import com.github.noxchimaera.xebra.nodes.readers.XmlNodeReader;
import com.github.noxchimaera.xebra.nodes.writers.ListXmlNodeWriter;
import com.github.noxchimaera.xebra.nodes.writers.SimpleXmlNodeWriter;
import com.github.noxchimaera.xebra.nodes.writers.XmlNodeWriter;
import com.github.noxchimaera.xebra.utils.XmlUtils;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author Max Balushkin
 */
public class ListXmlNodeTest {

    private <T> ListXmlNodeWriter<T> list(XmlNodeWriter<T> writer) {
        return new ListXmlNodeWriter<>(writer);
    }

    private <T>ListXmlNodeReader<T> list(XmlNodeReader<T> reader) {
        return new ListXmlNodeReader<>(reader);
    }

    @Test public void test_list_xml_node() throws Exception {
        Phone p1 = new Phone();
        p1.number = "123-456-789";
        Phone p2 = new Phone();
        p2.number = "987-654-321";

        Person person = new Person();
        person.Phones = Arrays.asList(p1, p2);

        XmlNodeWriter<Phone> freePhoneWriter = new SimpleXmlNodeWriter<>("Phone");
        XmlNodeWriter<Person> freePersonWriter = new SimpleXmlNodeWriter<Person>("Person")
            .child(list(freePhoneWriter).pinned(i -> i.Phones));

        XmlNodeReader<Phone> freePhoneReader = new SimpleXmlNodeReader<>("Phone", Phone::new);
        XmlNodeReader<Person> freePersonReader = new SimpleXmlNodeReader<Person>("Person", Person::new)
            .child(list(freePhoneReader).pinned((i, phones) -> i.Phones = phones));

        Document doc = XmlUtils.createDocument();
        Element root = doc.createElement("Root");
        freePersonWriter.write(doc, root, person);

        Person loaded = freePersonReader.read(root, ReadStrategy.Auto);

        assertNotNull(loaded);
        assertNotNull(loaded.Phones);
        assertEquals(person.Phones.size(), loaded.Phones.size());
    }

}
