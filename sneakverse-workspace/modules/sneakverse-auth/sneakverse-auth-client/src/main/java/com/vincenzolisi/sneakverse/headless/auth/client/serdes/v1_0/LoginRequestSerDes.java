package com.vincenzolisi.sneakverse.headless.auth.client.serdes.v1_0;

import com.vincenzolisi.sneakverse.headless.auth.client.dto.v1_0.LoginRequest;
import com.vincenzolisi.sneakverse.headless.auth.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Vincenzo Lisi
 * @generated
 */
@Generated("")
public class LoginRequestSerDes {

	public static LoginRequest toDTO(String json) {
		LoginRequestJSONParser loginRequestJSONParser =
			new LoginRequestJSONParser();

		return loginRequestJSONParser.parseToDTO(json);
	}

	public static LoginRequest[] toDTOs(String json) {
		LoginRequestJSONParser loginRequestJSONParser =
			new LoginRequestJSONParser();

		return loginRequestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(LoginRequest loginRequest) {
		if (loginRequest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (loginRequest.getEmail() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"email\": ");

			sb.append("\"");

			sb.append(_escape(loginRequest.getEmail()));

			sb.append("\"");
		}

		if (loginRequest.getPassword() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"password\": ");

			sb.append("\"");

			sb.append(_escape(loginRequest.getPassword()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		LoginRequestJSONParser loginRequestJSONParser =
			new LoginRequestJSONParser();

		return loginRequestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(LoginRequest loginRequest) {
		if (loginRequest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (loginRequest.getEmail() == null) {
			map.put("email", null);
		}
		else {
			map.put("email", String.valueOf(loginRequest.getEmail()));
		}

		if (loginRequest.getPassword() == null) {
			map.put("password", null);
		}
		else {
			map.put("password", String.valueOf(loginRequest.getPassword()));
		}

		return map;
	}

	public static class LoginRequestJSONParser
		extends BaseJSONParser<LoginRequest> {

		@Override
		protected LoginRequest createDTO() {
			return new LoginRequest();
		}

		@Override
		protected LoginRequest[] createDTOArray(int size) {
			return new LoginRequest[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "email")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "password")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			LoginRequest loginRequest, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "email")) {
				if (jsonParserFieldValue != null) {
					loginRequest.setEmail((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "password")) {
				if (jsonParserFieldValue != null) {
					loginRequest.setPassword((String)jsonParserFieldValue);
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}