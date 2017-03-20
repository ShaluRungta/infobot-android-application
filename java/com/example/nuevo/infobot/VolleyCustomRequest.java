package com.example.nuevo.infobot;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Map;

/*
Volley Custom request to send params with the post request
 */
public class VolleyCustomRequest extends Request<JSONObject> {

	//define the variables
	private Listener<JSONObject> listener;
	private Map<String, String> params;

	//constructor
	public VolleyCustomRequest(String url, Map<String, String> params,
							   Listener<JSONObject> reponseListener, ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		this.listener = reponseListener;
		this.params = params;
	}

	//constructor
	public VolleyCustomRequest(int method, String url,
							   Map<String, String> params, Listener<JSONObject> reponseListener,
							   ErrorListener errorListener) {
		super(method, url, errorListener);
		this.listener = reponseListener;
		this.params = params;
	}

	@Override
	protected Map<String, String> getParams()
			throws com.android.volley.AuthFailureError {
		return params;
	};

	@Override
	protected void deliverResponse(JSONObject response) {
		listener.onResponse(response);
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			return Response.success(new JSONObject(jsonString),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
	}
}
