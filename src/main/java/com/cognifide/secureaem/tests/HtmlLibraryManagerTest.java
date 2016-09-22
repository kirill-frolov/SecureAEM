package com.cognifide.secureaem.tests;

import com.cognifide.secureaem.AbstractTest;
import com.cognifide.secureaem.Configuration;
import com.cognifide.secureaem.UserHelper;
import com.cognifide.secureaem.markers.AuthorTest;
import com.cognifide.secureaem.markers.PublishTest;

public class HtmlLibraryManagerTest extends AbstractTest
		implements AuthorTest, PublishTest, OsgiConfigurationTest {

	public HtmlLibraryManagerTest(Configuration config) {
		super(config);
	}

	@Override public boolean doTest(String url, String instanceName) throws Exception {
		String[] user = AuthorTest.ENVIRONMENT_NAME.equals(instanceName) ?
				UserHelper.splitUser(config.getStringValue("authorUser", "admin:admin")) :
				UserHelper.splitUser(config.getStringValue("publishUser", "admin:admin"));
		String configurationEndpoint = url
				+ "/system/console/configMgr/com.adobe.granite.ui.clientlibs.impl.HtmlLibraryManagerImpl.json";
		String body = getJsonBodyOfOsgiConfiguration(configurationEndpoint, user, instanceName);
		checkBooleanValue(getBooleanValueFromJson("htmllibmanager.minify", body), true, "Minify",
				instanceName);
		checkBooleanValue(getBooleanValueFromJson("htmllibmanager.gzip", body), true, "Gzip", instanceName);
		checkBooleanValue(getBooleanValueFromJson("htmllibmanager.debug", body), false, "Debug",
				instanceName);
		checkBooleanValue(getBooleanValueFromJson("htmllibmanager.timing", body), false, "Timing",
				instanceName);
		return getErrorMessages().isEmpty();
	}
}

