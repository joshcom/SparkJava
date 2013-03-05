//
//  Copyright (c) 2013 Financial Business Systems, Inc. All rights reserved.
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//

package com.sparkplatform.api.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.sparkplatform.api.SparkAPIClientException;
import com.sparkplatform.api.core.ApiParameter;
import com.sparkplatform.api.core.Configuration;
import com.sparkplatform.api.core.MockClient;
import com.sparkplatform.api.core.PropertyAsserter;
import com.sparkplatform.api.core.Response;
import com.sparkplatform.api.services.ContactService;

public class ContactTest {
	MockClient c;
	@Before
	public void setup() throws SparkAPIClientException{
		Configuration cf = new Configuration();
		cf.setApiUser("SOME_GUY");
		c = MockClient.mock(cf);
	}

	@Test
	public void testProperties(){
		PropertyAsserter.assertBasicGetterSetterBehavior(new Contact());
	}

	@Test
	public void testGet() throws SparkAPIClientException {
		c.stubGet("/contacts", "contacts.json", 200);
		Response r = c.get("/contacts", new HashMap<ApiParameter, String>());
		assertNotNull(r);
		Contact m = r.getResults(Contact.class).get(0);
		validate(m);
	}

	@Test
	public void testService() throws SparkAPIClientException {
		c.stubGet("/contacts", "contacts.json", 200);
		ContactService s = new ContactService(c);
		Contact m = s.find().get(0);
		validate(m);
	}
	private void validate(Contact m){
		assertEquals("20101230223226074201000000", m.getId());
		assertEquals("contact1@fbsdata.com", m.getPrimaryEmail());
		assertEquals("Contact One", m.getDisplayName());
		assertEquals(0, m.getAttributes().size());
	}
}
