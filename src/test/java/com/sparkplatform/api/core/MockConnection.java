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

package com.sparkplatform.api.core;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sparkplatform.api.SparkAPIClientException;

@SuppressWarnings("unchecked")
public class MockConnection extends Connection<Response> {
	private static final Logger logger = Logger.getLogger(MockConnection.class);

	JsonResponseHandler parser = new JsonResponseHandler();
	Connection<Response> c;
	
	public MockConnection() {
		c = mock(Connection.class);
	}
	
	public void stubGet(String path, String fixture, int status) throws SparkAPIClientException {
		logger.debug("STUBBED: " + path);
		Response r = parseFile(fixture, status);
		when(c.get(path)).thenReturn(r);
	}
	public void stubPost(String path, String body, String fixture, int status) throws SparkAPIClientException {
		Response r = parseFile(fixture, status);
		when(c.post(path, body)).thenReturn(r);
	}

	public void stubDelete(String path, String fixture, int status) throws SparkAPIClientException {
		Response r = parseFile(fixture, status);
		when(c.delete(path)).thenReturn(r);
	}
	public void stubPut(String path, String body, String fixture, int status) throws SparkAPIClientException {
		Response r = parseFile(fixture, status);
		when(c.put(path, body)).thenReturn(r);
	}
	
	private Response parseFile(String fixture, int status) throws SparkAPIClientException {
		try {
			File f = new File("src/test/fixtures/" + fixture);
			return parser.parseResponse(new FileInputStream(f), status);
		} catch (IOException e) {
			throw new SparkAPIClientException("Mock test failed to find json file " + fixture);
		}
	}

	@Override
	public Response get(String path, Map<String, String> options)
			throws SparkAPIClientException {
		return c.get(path);
	}

	@Override
	public Response post(String path, String body, Map<String, String> options)
			throws SparkAPIClientException {
		return c.post(path, body);
	}

	@Override
	public Response put(String path, String body, Map<String, String> options)
			throws SparkAPIClientException {
		return c.put(path, body);
	}

	@Override
	public Response delete(String path, Map<String, String> options)
			throws SparkAPIClientException {
		return c.delete(path);
	}
	
}
