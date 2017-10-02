package ua.application;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import ua.application.pages.index.AuthenticationPage;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage {
	private WicketTester tester;

	@Before
	public void setUp() {
		tester = new WicketTester(new WicketApplication());
	}

	@Test
	public void homepageRendersSuccessfully() {
		// start and render the test page
		tester.startPage(AuthenticationPage.class);

		// assert rendered page class
		tester.assertRenderedPage(AuthenticationPage.class);
	}
}
