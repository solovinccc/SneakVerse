package com.vincenzolisi.sneakverse.headless.auth.client.dto.v1_0;

import com.vincenzolisi.sneakverse.headless.auth.client.function.UnsafeSupplier;
import com.vincenzolisi.sneakverse.headless.auth.client.serdes.v1_0.RegisterRequestSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Vincenzo Lisi
 * @generated
 */
@Generated("")
public class RegisterRequest implements Cloneable, Serializable {

	public static RegisterRequest toDTO(String json) {
		return RegisterRequestSerDes.toDTO(json);
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPassword(
		UnsafeSupplier<String, Exception> passwordUnsafeSupplier) {

		try {
			password = passwordUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String password;

	@Override
	public RegisterRequest clone() throws CloneNotSupportedException {
		return (RegisterRequest)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RegisterRequest)) {
			return false;
		}

		RegisterRequest registerRequest = (RegisterRequest)object;

		return Objects.equals(toString(), registerRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return RegisterRequestSerDes.toJSON(this);
	}

}