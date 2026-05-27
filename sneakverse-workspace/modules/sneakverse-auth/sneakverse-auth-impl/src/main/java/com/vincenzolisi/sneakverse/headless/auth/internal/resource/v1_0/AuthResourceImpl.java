package com.vincenzolisi.sneakverse.headless.auth.internal.resource.v1_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.security.auth.Authenticator;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.vincenzolisi.sneakverse.headless.auth.dto.v1_0.LoginRequest;
import com.vincenzolisi.sneakverse.headless.auth.dto.v1_0.RegisterRequest;
import com.vincenzolisi.sneakverse.headless.auth.dto.v1_0.SessionUser;
import com.vincenzolisi.sneakverse.headless.auth.resource.v1_0.AuthResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Component(properties = "OSGI-INF/liferay/rest/v1_0/auth.properties", scope = ServiceScope.PROTOTYPE, service = AuthResource.class)
public class AuthResourceImpl extends BaseAuthResourceImpl {

	private static final Log _log = LogFactoryUtil.getLog(AuthResourceImpl.class);

	@Reference
	private UserLocalService userLocalService;

	@AccessControlled(guestAccessEnabled = true)
	@Override
	public SessionUser loginUser(LoginRequest loginRequest) throws Exception {
		_log.info("=== LOGIN ATTEMPT ===");
		_log.info("Email: " + loginRequest.getEmail());
		_log.info("Password length: "
				+ (loginRequest.getPassword() != null ? loginRequest.getPassword().length() : "NULL"));

		try {
			long companyId = PortalUtil.getDefaultCompanyId();
			_log.info("CompanyId: " + companyId);

			User user = null;
			try {
				user = userLocalService.getUserByEmailAddress(companyId, loginRequest.getEmail());
				_log.info("User found - ID: " + user.getUserId());
				_log.info("User status: " + user.getStatus());
				_log.info("User active: " + user.isActive());
				_log.info("User emailVerified: " + user.isEmailAddressVerified());
				_log.info("User passwordReset: " + user.isPasswordReset());
				_log.info("User passwordEncrypted: " + user.isPasswordEncrypted());
				_log.info("User password hash: " + user.getPassword());
				_log.info("User lockout: " + user.isLockout());
				_log.info("User agreedToTerms: " + user.isAgreedToTermsOfUse());
			} catch (Exception e) {
				_log.error("User NOT found for email: " + loginRequest.getEmail(), e);
				throw new WebApplicationException("Utente non trovato", Response.Status.UNAUTHORIZED);
			}

			Map<String, String[]> headerMap = new HashMap<>();
			Map<String, String[]> parameterMap = new HashMap<>();
			Map<String, Object> resultsMap = new HashMap<>();

			_log.info("Calling authenticateByEmailAddress...");
			int authResult = userLocalService.authenticateByEmailAddress(
					companyId,
					loginRequest.getEmail(),
					loginRequest.getPassword(),
					headerMap,
					parameterMap,
					resultsMap);

			_log.info("Auth result code: " + authResult);
			_log.info("Results map: " + resultsMap);

			if (authResult == Authenticator.SUCCESS) {
				_log.info("Login SUCCESS for: " + loginRequest.getEmail());
				return mapUserToSessionUser(user);
			} else {
				_log.warn("Login FAILED for: " + loginRequest.getEmail() + " | code: " + authResult + " | resultsMap: "
						+ resultsMap);
				throw new WebApplicationException(
						"Credenziali errate (Codice Liferay: " + authResult + ", Info: " + resultsMap + ")",
						Response.Status.UNAUTHORIZED);
			}

		} catch (WebApplicationException we) {
			throw we;
		} catch (Exception e) {
			_log.error("Unexpected error during login: " + e.getMessage(), e);
			throw new WebApplicationException("Errore Server: " + e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@AccessControlled(guestAccessEnabled = true)
	@Override
	public SessionUser registerUser(RegisterRequest registerRequest) throws Exception {
		try {
			long companyId = PortalUtil.getDefaultCompanyId();
			long defaultAdminUserId = userLocalService.getDefaultUserId(companyId);
			String screenName = registerRequest.getEmail().split("@")[0].replaceAll("[^a-zA-Z0-9]", "")
					+ System.currentTimeMillis();

			ServiceContext serviceContext = new ServiceContext();
			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(defaultAdminUserId);

			// 1. CREAZIONE: Chiediamo a Liferay di generare una password casuale
			// (autoPassword = true)
			// Passiamo null ai campi password per evitare qualsiasi interferenza.
			User user = userLocalService.addUser(
					defaultAdminUserId, companyId,
					true, null, null, // <-- LA MAGIA È QUI: autoPassword a true
					false, screenName, registerRequest.getEmail(), LocaleUtil.getDefault(),
					registerRequest.getFirstName(), "", registerRequest.getLastName(), 0, 0,
					true, 1, 1, 1990, "", 0, null, null, null, null, false, serviceContext);

			// 2. CRITTOGRAFIA: Ora che l'utente esiste sano e salvo, gli forziamo la TUA
			// password
			userLocalService.updatePassword(
					user.getUserId(),
					registerRequest.getPassword(),
					registerRequest.getPassword(),
					false // non obblighiamo al reset
			);

			// 3. ATTIVAZIONE: Lo approviamo definitivamente
			userLocalService.updateStatus(user.getUserId(), 0, serviceContext);

			return mapUserToSessionUser(user);

		} catch (WebApplicationException we) {
			throw we;
		} catch (com.liferay.portal.kernel.exception.UserEmailAddressException e) {
			throw new WebApplicationException("Email già registrata!", Response.Status.CONFLICT);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException("Errore Server: " + e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	private User createLiferayUser(RegisterRequest req) throws Exception {
		long companyId = PortalUtil.getDefaultCompanyId();
		long defaultAdminUserId = userLocalService.getDefaultUserId(companyId);
		String screenName = req.getEmail().split("@")[0].replaceAll("[^a-zA-Z0-9]", "") + System.currentTimeMillis();

		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(defaultAdminUserId);

		return userLocalService.addUser(
				defaultAdminUserId, companyId, false, req.getPassword(), req.getPassword(), false,
				screenName, req.getEmail(), LocaleUtil.getDefault(),
				req.getFirstName(), "", req.getLastName(), 0, 0,
				true, 1, 1, 1990, "", 0, null, null, null, null, false, serviceContext);
	}

	@AccessControlled(guestAccessEnabled = true)
	@Override
	public SessionUser getCurrentUser() throws Exception {
		SessionUser sessionUser = new SessionUser();

		if (contextUser != null && !contextUser.isGuestUser()) {
			sessionUser.setIsLoggedIn(true);
			sessionUser.setId(Long.valueOf(contextUser.getUserId()));
			sessionUser.setEmail(contextUser.getEmailAddress());
			sessionUser.setFirstName(contextUser.getFirstName());
			sessionUser.setLastName(contextUser.getLastName());
		} else {
			sessionUser.setIsLoggedIn(false);
		}

		return sessionUser;
	}

	private SessionUser mapUserToSessionUser(User user) {
		SessionUser sessionUser = new SessionUser();
		sessionUser.setId(user.getUserId());
		sessionUser.setEmail(user.getEmailAddress());
		sessionUser.setFirstName(user.getFirstName());
		sessionUser.setLastName(user.getLastName());
		sessionUser.setIsLoggedIn(true);
		return sessionUser;
	}
}