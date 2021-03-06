/*
 * Copyright (c) 2015-2018 Koninklijke Philips N.V.
 * All rights reserved.
 */
package com.philips.platform.appinfra.aikm;

import android.content.Context;
import android.util.Log;

import com.philips.platform.appinfra.AppInfra;
import com.philips.platform.appinfra.aikm.exception.AIKMJsonFileNotFoundException;
import com.philips.platform.appinfra.aikm.model.AIKMService;
import com.philips.platform.appinfra.logging.LoggingInterface;
import com.philips.platform.appinfra.servicediscovery.model.AIKMResponse;
import com.philips.platform.appinfra.servicediscovery.model.ServiceDiscoveryService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AiKmHelperTest {

    private AiKmHelper aiKmHelper;
    private AppInfra mAppInfraMock;
    private InputStream inputStream;

    @Before
    public void setUp() throws Exception {
        mAppInfraMock = mock(AppInfra.class);
        LoggingInterface loggingInterfaceMock = mock(LoggingInterface.class);
        when(mAppInfraMock.getLogging()).thenReturn(loggingInterfaceMock);

        try {
            inputStream = getInstrumentation().getContext().getResources().getAssets().open("AIKMap.json");
        } catch (IOException e) {
            Log.e("error ", " while reading json");
        }

        aiKmHelper = new AiKmHelper(mAppInfraMock) {
            @Override
            InputStream getInputStream(Context mContext, String fileName) throws AIKMJsonFileNotFoundException {
                return inputStream;
            }
        };
        aiKmHelper.init(mAppInfraMock);
    }

    @Test
    public void testGettingSeed() throws NoSuchAlgorithmException {
        String groupId = "appinfra.languagePack2";
        int index = 1;
        String key = "client_id";
        assertNull(aiKmHelper.getValue("", 0, "test"));
        assertNull(aiKmHelper.getValue("test", 0, ""));
        assertNull(aiKmHelper.getValue(null, 0, ""));
        assertNull(aiKmHelper.getValue("test", 0, null));
        String seed = aiKmHelper.getValue(groupId, index, key);
        assertEquals(4, seed.length());
    }

    @Test
    public void testGettingIndex() {
        String indexData = "https://philips.com/0";
        URL url;
        try {
            url = new URL(indexData);
            assertEquals(aiKmHelper.getGroomIndex(url.toString()), "0");
        } catch (MalformedURLException e) {
            Log.e("error ", " while fetching url");
        }

        indexData = "https://philips.com/22";
        assertEquals(aiKmHelper.getGroomIndex(indexData), "22");
        indexData = "https://philips.com/";
        assertNull(aiKmHelper.getGroomIndex(indexData));
        indexData = "";
        assertNull(aiKmHelper.getGroomIndex(indexData));
        assertNull(aiKmHelper.getGroomIndex(null));
    }

    @Test
    public void testGettingIndexWithSplit() {
        String indexData = "https://philips.com/0";
        URL url;
        try {
            url = new URL(indexData);
            assertEquals(aiKmHelper.getGroomIndexWithSplit(url.toString()), "0");
        } catch (MalformedURLException e) {
            Log.e("error ", " while fetching url");
        }

        indexData = "https://philips.com/22";
        assertEquals(aiKmHelper.getGroomIndexWithSplit(indexData), "22");
        indexData = "https://philips.com/";
        assertNull(aiKmHelper.getGroomIndexWithSplit(indexData));
        indexData = "";
        assertNull(aiKmHelper.getGroomIndexWithSplit(indexData));
        assertNull(aiKmHelper.getGroomIndexWithSplit(null));
    }

    @Test
    public void testGettingMd5ValueInHex() throws NoSuchAlgorithmException {
        assertNull(aiKmHelper.getAilGroomInHex(null));
        assertNotNull(aiKmHelper.getAilGroomInHex("testing"));
    }

    @Test
    public void testInit() {
        ExpectedException thrown = ExpectedException.none();
        thrown.expect(AIKMJsonFileNotFoundException.class);
        thrown.expectMessage("AIKeyBag.json file not found in assets folder");
        try {
            try {
                aiKmHelper.init(mAppInfraMock);
            } catch (JSONException e) {
                assertEquals(e.getMessage(), AIKMService.AIKMapError.INVALID_JSON.name());
            }
        } catch (AIKMJsonFileNotFoundException e) {
            Log.e("error ", " aibag.json file not found");
            assertEquals(e.getMessage(), "AIKMap.json file not found in assets folder");
        }
    }

    @Test
    public void testConvertingToHex() {
        String hexString = "52616a612052616d204d6f68616e20526f79";
        assertEquals(aiKmHelper.convertData(hexString), "Raja Ram Mohan Roy");
        String testString = aiKmHelper.convertData("c2b3c2a5085a2dc3a91672c29fc28e55c2955bc2a4c282656cc3bc");
        assertNotNull(testString);
    }

    @Test
    public void testObfuscate() {
        String obfuscate = aiKmHelper.ailGroom("Raja Ram Mohan Roy", 0XAEF7);
        assertNotEquals("Raja Ram Mohan Roy", obfuscate);
        assertEquals(aiKmHelper.ailGroom(obfuscate, 0XAEF7), "Raja Ram Mohan Roy");
        assertNotEquals("Raja Ram Mohan Roy xxx", aiKmHelper.ailGroom(obfuscate, 0XAEF7));
    }

    @Test
    public void testGetAppendedServiceIds() {
        String[] serviceIds = {"serviceId1", "serviceId2", "", null};
        ArrayList<String> appendedServiceIds = aiKmHelper.getAppendedGrooms(Arrays.asList(serviceIds));
        for (int i = 0; i < appendedServiceIds.size(); i++) {
            assertTrue(appendedServiceIds.get(i).contains(".kindex"));
            assertNotNull(appendedServiceIds.get(i));
        }
    }

    @Test
    public void testMappingData() throws Exception {
        assertNotNull(aiKmHelper.mapData(new JSONObject(), 0, "service_id"));
    }

    @Test
    public void testMapAndValidateKey() throws AIKMJsonFileNotFoundException {
        aiKmHelper = new AiKmHelper(mAppInfraMock) {
            @Override
            Object getAilGroomProperties(String serviceId) {
                return new JSONArray();
            }

            @Override
            InputStream getInputStream(Context mContext, String fileName) throws AIKMJsonFileNotFoundException {
                return inputStream;
            }
        };
        try {
            aiKmHelper.init(mAppInfraMock);
        } catch (JSONException e) {
            assertEquals(e.getMessage(), AIKMService.AIKMapError.INVALID_JSON.name());
        }
        ServiceDiscoveryService serviceDiscovery = new ServiceDiscoveryService();
        serviceDiscovery.setmError("something went wrong");
        AIKMService aikmService = new AIKMService();
        aiKmHelper.mapAndValidateGroom(aikmService, null, "0");
        assertEquals(aikmService.getAIKMapError(), AIKMService.AIKMapError.NO_SERVICE_FOUND);

        aiKmHelper.mapAndValidateGroom(aikmService, "service_id", "string");
        assertEquals(AIKMService.AIKMapError.INVALID_INDEX_URL, aikmService.getAIKMapError());

        aiKmHelper = new AiKmHelper(mAppInfraMock) {
            @Override
            Object getAilGroomProperties(String serviceId) {
                return new JSONObject();
            }
        };
        aiKmHelper.mapAndValidateGroom(aikmService, "service_id", "1");
        assertEquals(AIKMService.AIKMapError.INVALID_JSON, aikmService.getAIKMapError());

        JSONObject someJsonObject = new JSONObject();
        try {
            someJsonObject.put("clientId", "4c73b365");
            final JSONArray someJsonArray = new JSONArray(someJsonObject);
            aiKmHelper = new AiKmHelper(mAppInfraMock) {
                @Override
                Object getAilGroomProperties(String serviceId) {
                    return someJsonArray;
                }
            };
            aiKmHelper.mapAndValidateGroom(aikmService, "service_id", "1");
            assertNotNull(aikmService.getAIKMap());
            assertEquals(aikmService.getAIKMap().get("clientId"), "test");
        } catch (JSONException e) {
            Log.e("error ", " in json structure");
        }
    }

    @Test
    public void testMapResponse() {
        Map<String, ServiceDiscoveryService> stringServiceDiscoveryServiceMap = new HashMap<>();
        List<AIKMService> aiKmServices = new ArrayList<>();
        ServiceDiscoveryService serviceDiscoveryService = new ServiceDiscoveryService();

        serviceDiscoveryService.init("en_GB", "some_url");
        stringServiceDiscoveryServiceMap.put("service_id", serviceDiscoveryService);
        Map<String, ServiceDiscoveryService> map = new HashMap<>();
        map.put("service_id.kindex", null);
        aiKmHelper.mapResponse(map, aiKmServices, stringServiceDiscoveryServiceMap);
        assertEquals(aiKmServices.get(0).getAIKMapError(), AIKMService.AIKMapError.NO_SERVICE_FOUND);
        ServiceDiscoveryService serviceDiscoveryServiceKindex = new ServiceDiscoveryService();
        map.put("service_id.kindex", serviceDiscoveryServiceKindex);
        aiKmServices = new ArrayList<>();
        aiKmHelper.mapResponse(map, aiKmServices, stringServiceDiscoveryServiceMap);
        assertEquals(aiKmServices.get(0).getAIKMapError(), AIKMService.AIKMapError.NO_URL_FOUND);
    }

    @Test
    public void testMapAndValidateKeyNew() throws AIKMJsonFileNotFoundException {
        aiKmHelper = new AiKmHelper(mAppInfraMock) {
            @Override
            Object getAilGroomProperties(String serviceId) {
                return new JSONArray();
            }

            @Override
            InputStream getInputStream(Context mContext, String fileName) throws AIKMJsonFileNotFoundException {
                return inputStream;
            }
        };
        try {
            aiKmHelper.init(mAppInfraMock);
        } catch (JSONException e) {
            assertEquals(e.getMessage(), AIKMService.AIKMapError.INVALID_JSON.name());
        }
        ServiceDiscoveryService serviceDiscovery = new ServiceDiscoveryService();
        serviceDiscovery.setmError("something went wrong");
        AIKMResponse aikmResponse = new AIKMResponse();
        aiKmHelper.mapAndValidateGroom(null, "0", aikmResponse);
        assertEquals(aikmResponse.getkError(), AIKMResponse.KError.DATA_NOT_FOUND);

        aiKmHelper.mapAndValidateGroom("service_id", "string", aikmResponse);
        assertEquals(AIKMResponse.KError.INVALID_INDEX_URL, aikmResponse.getkError());

        aiKmHelper = new AiKmHelper(mAppInfraMock) {
            @Override
            Object getAilGroomProperties(String serviceId) {
                return new JSONObject();
            }
        };
        aiKmHelper.mapAndValidateGroom("service_id", "1", aikmResponse);
        assertEquals(AIKMResponse.KError.INVALID_JSON, aikmResponse.getkError());

        JSONObject someJsonObject = new JSONObject();
        try {
            someJsonObject.put("clientId", "4c73b365");
            final JSONArray someJsonArray = new JSONArray(someJsonObject);
            aiKmHelper = new AiKmHelper(mAppInfraMock) {
                @Override
                Object getAilGroomProperties(String serviceId) {
                    return someJsonArray;
                }
            };
            aiKmHelper.mapAndValidateGroom("service_id", "1", aikmResponse);
            assertNotNull(aikmResponse.getkMap());
            assertEquals(aikmResponse.getkMap().get("clientId"), "test");
        } catch (JSONException e) {
            Log.e("error ", " in json structure");
        }
    }
}