package com.vincenzolisi.sneakverse.headless.auth.client.dto.v1_0;

import com.vincenzolisi.sneakverse.headless.auth.client.function.UnsafeSupplier;
import com.vincenzolisi.sneakverse.headless.auth.client.serdes.v1_0.LoginRequestSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Vincenzo Lisi
 * @generated
 */
@Generated("")
public class LoginRequest implements Cloneable, Serializable {

	public static LoginRequest toDTO(String json) {
		return LoginRequestSerDes.toDTO(json);
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
	public LoginRequest clone() throws CloneNotSupportedException {
		return (LoginRequest)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LoginRequest)) {
			return false;
		}

		LoginRequest loginRequest = (LoginRequest)object;

		return Objects.equals(toString(), loginRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return LoginRequestSerDes.toJSON(this);
	}

}