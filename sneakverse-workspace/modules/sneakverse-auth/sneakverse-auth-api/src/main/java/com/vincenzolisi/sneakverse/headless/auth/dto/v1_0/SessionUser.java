package com.vincenzolisi.sneakverse.headless.auth.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Vincenzo Lisi
 * @generated
 */
@Generated("")
@GraphQLName("SessionUser")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "SessionUser")
public class SessionUser implements Serializable {

	public static SessionUser toDTO(String json) {
		return ObjectMapperUtil.readValue(SessionUser.class, json);
	}

	public static SessionUser unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(SessionUser.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	public String getEmail() {
		if (_emailSupplier != null) {
			email = _emailSupplier.get();

			_emailSupplier = null;
		}

		return email;
	}

	public void setEmail(String email) {
		this.email = email;

		_emailSupplier = null;
	}

	@JsonIgnore
	public void setEmail(
		UnsafeSupplier<String, Exception> emailUnsafeSupplier) {

		_emailSupplier = () -> {
			try {
				return emailUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String email;

	@JsonIgnore
	private Supplier<String> _emailSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getFirstName() {
		if (_firstNameSupplier != null) {
			firstName = _firstNameSupplier.get();

			_firstNameSupplier = null;
		}

		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;

		_firstNameSupplier = null;
	}

	@JsonIgnore
	public void setFirstName(
		UnsafeSupplier<String, Exception> firstNameUnsafeSupplier) {

		_firstNameSupplier = () -> {
			try {
				return firstNameUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String firstName;

	@JsonIgnore
	private Supplier<String> _firstNameSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getId() {
		if (_idSupplier != null) {
			id = _idSupplier.get();

			_idSupplier = null;
		}

		return id;
	}

	public void setId(Long id) {
		this.id = id;

		_idSupplier = null;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		_idSupplier = () -> {
			try {
				return idUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long id;

	@JsonIgnore
	private Supplier<Long> _idSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Boolean getIsLoggedIn() {
		if (_isLoggedInSupplier != null) {
			isLoggedIn = _isLoggedInSupplier.get();

			_isLoggedInSupplier = null;
		}

		return isLoggedIn;
	}

	public void setIsLoggedIn(Boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;

		_isLoggedInSupplier = null;
	}

	@JsonIgnore
	public void setIsLoggedIn(
		UnsafeSupplier<Boolean, Exception> isLoggedInUnsafeSupplier) {

		_isLoggedInSupplier = () -> {
			try {
				return isLoggedInUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean isLoggedIn;

	@JsonIgnore
	private Supplier<Boolean> _isLoggedInSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getLastName() {
		if (_lastNameSupplier != null) {
			lastName = _lastNameSupplier.get();

			_lastNameSupplier = null;
		}

		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;

		_lastNameSupplier = null;
	}

	@JsonIgnore
	public void setLastName(
		UnsafeSupplier<String, Exception> lastNameUnsafeSupplier) {

		_lastNameSupplier = () -> {
			try {
				return lastNameUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String lastName;

	@JsonIgnore
	private Supplier<String> _lastNameSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SessionUser)) {
			return false;
		}

		SessionUser sessionUser = (SessionUser)object;

		return Objects.equals(toString(), sessionUser.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		String email = getEmail();

		if (email != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"email\": ");

			sb.append("\"");

			sb.append(_escape(email));

			sb.append("\"");
		}

		String firstName = getFirstName();

		if (firstName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"firstName\": ");

			sb.append("\"");

			sb.append(_escape(firstName));

			sb.append("\"");
		}

		Long id = getId();

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		Boolean isLoggedIn = getIsLoggedIn();

		if (isLoggedIn != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"isLoggedIn\": ");

			sb.append(isLoggedIn);
		}

		String lastName = getLastName();

		if (lastName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lastName\": ");

			sb.append("\"");

			sb.append(_escape(lastName));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.vincenzolisi.sneakverse.headless.auth.dto.v1_0.SessionUser",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
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
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof Map) {
						sb.append(_toJSON((Map<String, ?>)valueArray[i]));
					}
					else if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}