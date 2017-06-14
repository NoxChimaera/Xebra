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

package com.github.noxchimaera.attributes;

import com.github.noxchimaera.Occupation;
import com.github.noxchimaera.attributes.readers.PinnedXmlAttributeReader;
import com.github.noxchimaera.attributes.readers.SimpleXmlAttributeReader;
import com.github.noxchimaera.attributes.writers.PinnedXmlAttributeWriter;
import com.github.noxchimaera.attributes.writers.SimpleXmlAttributeWriter;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static org.junit.Assert.*;

/**
 * @author Max Balushkin
 */
public class XmlAttributeTest {

    @Test public void test_xml_attribute() throws Exception {
        SimpleXmlAttributeWriter<String> freeWorkplaceWriter
            = new SimpleXmlAttributeWriter<>("Workplace", attr -> attr);
        PinnedXmlAttributeWriter<Occupation, String> workplaceWriter
            = freeWorkplaceWriter.pinned(occupation -> occupation.Workplace);

        SimpleXmlAttributeReader<String> freeWorkplaceReader
            = new SimpleXmlAttributeReader<>("Workplace", attr -> attr);
        PinnedXmlAttributeReader<Occupation, String> workplaceReader
            = freeWorkplaceReader.pinned((occupation, attr) -> occupation.Workplace = attr);

        Occupation occupation = new Occupation();
        occupation.Workplace = "Acme Inc.";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("Occupation");
        workplaceWriter.write(root, occupation);

        Occupation loaded = new Occupation();
        workplaceReader.read(root, loaded);

        assertEquals(occupation.Workplace, loaded.Workplace);
    }

}