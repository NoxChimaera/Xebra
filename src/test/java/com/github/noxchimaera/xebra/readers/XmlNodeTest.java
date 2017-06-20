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

package com.github.noxchimaera.xebra.readers;

import com.github.noxchimaera.xebra.Occupation;
import com.github.noxchimaera.xebra.Person;
import com.github.noxchimaera.xebra.nodes.readers.ReadStrategy;
import com.github.noxchimaera.xebra.nodes.readers.SimpleXmlNodeReader;
import com.github.noxchimaera.xebra.nodes.writers.SimpleXmlNodeWriter;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static org.junit.Assert.assertNotNull;

/**
 * @author Max Balushkin
 */
public class XmlNodeTest {

    @Test public void test_xml_node() throws Exception {
        Occupation occupation = new Occupation();
        Person person = new Person();
        person.Occupation = occupation;

        SimpleXmlNodeWriter<Occupation> freeOccupationWriter = new SimpleXmlNodeWriter<>("Occupation");
        SimpleXmlNodeWriter<Person> freePersonWriter = new SimpleXmlNodeWriter<Person>("Person")
            .child(freeOccupationWriter.pinned(i -> i.Occupation));

        SimpleXmlNodeReader<Occupation> freeOccupationReader
            = new SimpleXmlNodeReader<>("Occupation", Occupation::new);
        SimpleXmlNodeReader<Person> freePersonReader = new SimpleXmlNodeReader<Person>("Person", Person::new)
            .child(freeOccupationReader.pinned((i, child) -> i.Occupation = child));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("Root");
        freePersonWriter.write(doc, root, person);

        Person loaded = freePersonReader.read(root, ReadStrategy.Auto);

        assertNotNull(loaded);
        assertNotNull(loaded.Occupation);
    }

}
