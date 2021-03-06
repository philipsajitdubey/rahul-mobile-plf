package com.philips.cdp.prodreg.model.metadata;

import junit.framework.TestCase;

import org.junit.Test;

/* Copyright (c) Koninklijke Philips N.V., 2016
* All rights are reserved. Reproduction or dissemination
 * in whole or in part is prohibited without the prior written
 * consent of the copyright holder.
*/
public class MetadataSerNumbSampleContentTest extends TestCase {

    MetadataSerNumbSampleContent serialNumberSampleContentTest;

    @Override
    public void setUp() throws Exception {
        serialNumberSampleContentTest = new MetadataSerNumbSampleContent();
    }

    public void testSetTitle() throws Exception {
        serialNumberSampleContentTest.setTitle("Find the serial number");
        assertEquals("Find the serial number", serialNumberSampleContentTest.getTitle());
    }

    public void testSetAsset() throws Exception {
        serialNumberSampleContentTest.setAsset("/consumerfiles/assets/img/registerproducts/HC.jpg");
        assertEquals("/consumerfiles/assets/img/registerproducts/HC.jpg", serialNumberSampleContentTest.getAsset());
    }

    public void testSetSnExample() throws Exception {
        serialNumberSampleContentTest.setSnExample("Example: 1344");
        assertEquals("Example: 1344", serialNumberSampleContentTest.getSnExample());
    }

    public void testSetSnFormat() throws Exception {
        serialNumberSampleContentTest.setSnFormat("cc");
        assertEquals("cc", serialNumberSampleContentTest.getSnFormat());
    }

    @Test
    public void testGetTitle() throws Exception {
        serialNumberSampleContentTest.setTitle("Find the serial number");
        assertEquals("Find the serial number", serialNumberSampleContentTest.getTitle());
    }

    @Test
    public void testGetAsset() throws Exception {
        serialNumberSampleContentTest.setAsset("/consumerfiles/assets/img/registerproducts/HC.jpg");
        assertEquals("/consumerfiles/assets/img/registerproducts/HC.jpg", serialNumberSampleContentTest.getAsset());
    }

    @Test
    public void testGetSnExample() throws Exception {
        serialNumberSampleContentTest.setSnExample("Example: 1344");
        assertEquals("Example: 1344", serialNumberSampleContentTest.getSnExample());
    }

    @Test
    public void testGetSnFormat() throws Exception {
        serialNumberSampleContentTest.setSnFormat("cc");
        assertEquals("cc", serialNumberSampleContentTest.getSnFormat());
    }
}