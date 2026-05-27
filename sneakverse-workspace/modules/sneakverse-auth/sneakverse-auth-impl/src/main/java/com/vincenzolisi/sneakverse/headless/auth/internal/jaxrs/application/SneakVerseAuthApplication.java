package com.vincenzolisi.sneakverse.headless.auth.internal.jaxrs.application;

import javax.annotation.Generated;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;

/**
 * @author Vincenzo Lisi
 * @generated
 */
@Component(
	property = {
		"liferay.jackson=false", "osgi.jaxrs.application.base=/sneakverse-auth",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.Vulcan)",
		"osgi.jaxrs.name=SneakVerseAuth.Rest"
	},
	service = Application.class
)
@Generated("")
public class SneakVerseAuthApplication extends Application {
}