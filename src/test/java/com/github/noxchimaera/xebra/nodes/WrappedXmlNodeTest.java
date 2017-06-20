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
import com.github.noxchimaera.xebra.Occupation;
import com.github.noxchimaera.xebra.Person;
import com.github.noxchimaera.xebra.nodes.readers.ReadStrategy;
import com.github.noxchimaera.xebra.nodes.readers.SimpleXmlNodeReader;
import com.github.noxchimaera.xebra.nodes.readers.WrappedXmlNodeReader;
import com.github.noxchimaera.xebra.nodes.readers.XmlNodeReader;
import com.github.noxchimaera.xebra.nodes.writers.SimpleXmlNodeWriter;
import com.github.noxchimaera.xebra.nodes.writers.WrappedXmlNodeWriter;
import com.github.noxchimaera.xebra.nodes.writers.XmlNodeWriter;
import com.github.noxchimaera.xebra.utils.XmlUtils;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static org.junit.Assert.assertNotNull;

/**
 * @author Max Balushkin
 */
public class WrappedXmlNodeTest {

    private <T> WrappedXmlNodeWriter<T> wrap(String tag, XmlNodeWriter<T> writer) {
        return new WrappedXmlNodeWriter<>(tag, writer);
    }

    private <T> WrappedXmlNodeReader<T> wrap(String tag, XmlNodeReader<T> writer) {
        return new WrappedXmlNodeReader<>(tag, writer);
    }

    @Test public void test_wrapped_xml_node() throws Exception {
        Occupation occupation = new Occupation();
        // occupation.Workplace = "foo";
        Person person = new Person();
        person.Occupation = occupation;

        XmlNodeWriter<Occupation> freeOccupationWriter = new SimpleXmlNodeWriter<Occupation>("Occupation");
            // .attribute(new SimpleXmlAttributeWriter<String>("Workplace", attr -> attr).pinned(i -> i.Workplace));
        XmlNodeWriter<Person> freePersonWriter = new SimpleXmlNodeWriter<Person>("Person")
            .child(wrap("Wrap", freeOccupationWriter).pinned(i -> i.Occupation));

        XmlNodeReader<Occupation> freeOccupationReader = new SimpleXmlNodeReader<>("Occupation", Occupation::new);
            // .attribute(new SimpleXmlAttributeReader<String>("Workplace", attr -> attr).pinned((i, attr) -> i.Workplace = attr));
        XmlNodeReader<Person> freePersonReader = new SimpleXmlNodeReader<Person>("Person", Person::new)
            .child(wrap("Wrap", freeOccupationReader).pinned((i, child) -> i.Occupation = child));

        Document doc = XmlUtils.createDocument();

        Element root = doc.createElement("Root");
        freePersonWriter.write(doc, root, person);

        Person loaded = freePersonReader.read(root, ReadStrategy.Auto);

        assertNotNull(loaded);
        assertNotNull(loaded.Occupation);
    }

}
