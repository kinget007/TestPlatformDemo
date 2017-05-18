
package com.king.yyl.service.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AbstractRequestHandler {
    private static int TIME_OUT = 30000;

    public HttpURLConnection makeHttpConnection(String requestUrl, String method, String query) throws IOException {
	URL httpUrl = new URL(requestUrl);

	HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
	connection.setConnectTimeout(TIME_OUT);
	connection.setReadTimeout(TIME_OUT);
	connection.setUseCaches(false);
	connection.setDoOutput(true);
	connection.setRequestMethod(method);
	connection.setRequestProperty("Content-Type", String.format("application/x-www-form-urlencoded;charset=%s", new Object[] { "UTF-8" }));
	OutputStream output = null;
	try {
	    output = connection.getOutputStream();
	    output.write(query.getBytes("UTF-8"));
	} finally {
	    if (output != null)
		output.close();
	}
	return connection;
    }
}
