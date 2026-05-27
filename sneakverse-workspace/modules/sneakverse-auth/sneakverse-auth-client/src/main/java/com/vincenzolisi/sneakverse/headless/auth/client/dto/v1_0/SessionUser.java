package com.vincenzolisi.sneakverse.headless.auth.client.dto.v1_0;

import com.vincenzolisi.sneakverse.headless.auth.client.function.UnsafeSupplier;
import com.vincenzolisi.sneakverse.headless.auth.client.serdes.v1_0.SessionUserSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Vincenzo Lisi
 * @generated
 */
@Generated("")
public class SessionUser implements Cloneable, Serializable {

	public static SessionUser toDTO(String json) {
		return SessionUserSerDes.toDTO(json);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmail(
		UnsafeSupplier<String, Exception> emailUnsafeSupplier) {

		try {
			email = emailUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String email;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setFirstName(
		UnsafeSupplier<String, Exception> firstNameUnsafeSupplier) {

		try {
			firstName = firstNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String firstName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public Boolean getIsLoggedIn() {
		return isLoggedIn;
	}

	public void setIsLoggedIn(Boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public void setIsLoggedIn(
		UnsafeSupplier<Boolean, Exception> isLoggedInUnsafeSupplier) {

		try {
			isLoggedIn = isLoggedInUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean isLoggedIn;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setLastName(
		UnsafeSupplier<String, Exception> lastNameUnsafeSupplier) {

		try {
			lastName = lastNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String lastName;

	@Override
	public SessionUser clone() throws CloneNotSupportedException {
		return (SessionUser)super.clone();
	}

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
		return SessionUserSerDes.toJSON(this);
	}

}