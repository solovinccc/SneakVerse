package com.vincenzolisi.sneakverse.headless.auth.client.serdes.v1_0;

import com.vincenzolisi.sneakverse.headless.auth.client.dto.v1_0.SessionUser;
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
public class SessionUserSerDes {

	public static SessionUser toDTO(String json) {
		SessionUserJSONParser sessionUserJSONParser =
			new SessionUserJSONParser();

		return sessionUserJSONParser.parseToDTO(json);
	}

	public static SessionUser[] toDTOs(String json) {
		SessionUserJSONParser sessionUserJSONParser =
			new SessionUserJSONParser();

		return sessionUserJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SessionUser sessionUser) {
		if (sessionUser == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (sessionUser.getEmail() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"email\": ");

			sb.append("\"");

			sb.append(_escape(sessionUser.getEmail()));

			sb.append("\"");
		}

		if (sessionUser.getFirstName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"firstName\": ");

			sb.append("\"");

			sb.append(_escape(sessionUser.getFirstName()));

			sb.append("\"");
		}

		if (sessionUser.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(sessionUser.getId());
		}

		if (sessionUser.getIsLoggedIn() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"isLoggedIn\": ");

			sb.append(sessionUser.getIsLoggedIn());
		}

		if (sessionUser.getLastName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lastName\": ");

			sb.append("\"");

			sb.append(_escape(sessionUser.getLastName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SessionUserJSONParser sessionUserJSONParser =
			new SessionUserJSONParser();

		return sessionUserJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SessionUser sessionUser) {
		if (sessionUser == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (sessionUser.getEmail() == null) {
			map.put("email", null);
		}
		else {
			map.put("email", String.valueOf(sessionUser.getEmail()));
		}

		if (sessionUser.getFirstName() == null) {
			map.put("firstName", null);
		}
		else {
			map.put("firstName", String.valueOf(sessionUser.getFirstName()));
		}

		if (sessionUser.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(sessionUser.getId()));
		}

		if (sessionUser.getIsLoggedIn() == null) {
			map.put("isLoggedIn", null);
		}
		else {
			map.put("isLoggedIn", String.valueOf(sessionUser.getIsLoggedIn()));
		}

		if (sessionUser.getLastName() == null) {
			map.put("lastName", null);
		}
		else {
			map.put("lastName", String.valueOf(sessionUser.getLastName()));
		}

		return map;
	}

	public static class SessionUserJSONParser
		extends BaseJSONParser<SessionUser> {

		@Override
		protected SessionUser createDTO() {
			return new SessionUser();
		}

		@Override
		protected SessionUser[] createDTOArray(int size) {
			return new SessionUser[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "email")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "firstName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "isLoggedIn")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "lastName")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			SessionUser sessionUser, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "email")) {
				if (jsonParserFieldValue != null) {
					sessionUser.setEmail((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "firstName")) {
				if (jsonParserFieldValue != null) {
					sessionUser.setFirstName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					sessionUser.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "isLoggedIn")) {
				if (jsonParserFieldValue != null) {
					sessionUser.setIsLoggedIn((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastName")) {
				if (jsonParserFieldValue != null) {
					sessionUser.setLastName((String)jsonParserFieldValue);
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