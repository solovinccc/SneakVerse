FROM liferay/portal:7.4.3.132-ga132

USER root

COPY portal-ext.properties /opt/liferay/portal-ext.properties
COPY portal-setup-wizard.properties /opt/liferay/portal-setup-wizard.properties

RUN sed -i 's|liferay.home=.*|liferay.home=/opt/liferay|' /opt/liferay/portal-setup-wizard.properties

COPY osgi /opt/liferay/osgi

RUN chown -R liferay:liferay /opt/liferay/osgi /opt/liferay/portal-ext.properties /opt/liferay/portal-setup-wizard.properties

USER liferay
