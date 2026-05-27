package com.vincenzolisi.sneakverse.headless.auth.client.serdes.v1_0;

import com.vincenzolisi.sneakverse.headless.auth.client.dto.v1_0.RegisterRequest;
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
public class RegisterRequestSerDes {

	public static RegisterRequest toDTO(String json) {
		RegisterRequestJSONParser registerRequestJSONParser =
			new RegisterRequestJSONParser();

		return registerRequestJSONParser.parseToDTO(json);
	}

	public static RegisterRequest[] toDTOs(String json) {
		RegisterRequestJSONParser registerRequestJSONParser =
			new RegisterRequestJSONParser();

		return registerRequestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(RegisterRequest registerRequest) {
		if (registerRequest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (registerRequest.getEmail() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"email\": ");

			sb.append("\"");

			sb.append(_escape(registerRequest.getEmail()));

			sb.append("\"");
		}

		if (registerRequest.getFirstName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"firstName\": ");

			sb.append("\"");

			sb.append(_escape(registerRequest.getFirstName()));

			sb.append("\"");
		}

		if (registerRequest.getLastName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lastName\": ");

			sb.append("\"");

			sb.append(_escape(registerRequest.getLastName()));

			sb.append("\"");
		}

		if (registerRequest.getPassword() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"password\": ");

			sb.append("\"");

			sb.append(_escape(registerRequest.getPassword()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		RegisterRequestJSONParser registerRequestJSONParser =
			new RegisterRequestJSONParser();

		return registerRequestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(RegisterRequest registerRequest) {
		if (registerRequest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (registerRequest.getEmail() == null) {
			map.put("email", null);
		}
		else {
			map.put("email", String.valueOf(registerRequest.getEmail()));
		}

		if (registerRequest.getFirstName() == null) {
			map.put("firstName", null);
		}
		else {
			map.put(
				"firstName", String.valueOf(registerRequest.getFirstName()));
		}

		if (registerRequest.getLastName() == null) {
			map.put("lastName", null);
		}
		else {
			map.put("lastName", String.valueOf(registerRequest.getLastName()));
		}

		if (registerRequest.getPassword() == null) {
			map.put("password", null);
		}
		else {
			map.put("password", String.valueOf(registerRequest.getPassword()));
		}

		return map;
	}

	public static class RegisterRequestJSONParser
		extends BaseJSONParser<RegisterRequest> {

		@Override
		protected RegisterRequest createDTO() {
			return new RegisterRequest();
		}

		@Override
		protected RegisterRequest[] createDTOArray(int size) {
			return new RegisterRequest[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "email")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "firstName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "lastName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "password")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			RegisterRequest registerRequest, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "email")) {
				if (jsonParserFieldValue != null) {
					registerRequest.setEmail((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "firstName")) {
				if (jsonParserFieldValue != null) {
					registerRequest.setFirstName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastName")) {
				if (jsonParserFieldValue != null) {
					registerRequest.setLastName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "password")) {
				if (jsonParserFieldValue != null) {
					registerRequest.setPassword((String)jsonParserFieldValue);
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