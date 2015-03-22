package org.kyll.myserver.appconfig;

import org.kyll.myserver.filter.SessionFilter;
import org.kyll.myserver.util.ConstUtils;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * User: Kyll
 * Date: 14-10-26 下午6:08
 */
public class Initializer implements WebApplicationInitializer {
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		WebApplicationContext context = this.getContext();
		servletContext.addListener(new ContextLoaderListener(context));

		servletContext.setInitParameter("javax.servlet.jsp.jstl.fmt.localizationContext", "i18n");

		ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("dispatcherServlet", new DispatcherServlet(context));
		dispatcherServlet.setLoadOnStartup(1);
		dispatcherServlet.addMapping("*.ctrl");

		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		characterEncodingFilter.addMappingForUrlPatterns(null, true, "/*");

		FilterRegistration.Dynamic openSessionInViewFilter = servletContext.addFilter("openSessionInViewFilter", new OpenSessionInViewFilter());
		openSessionInViewFilter.addMappingForUrlPatterns(null, true, "/*");

		FilterRegistration.Dynamic sessionFilter = servletContext.addFilter("sessionFilter", new SessionFilter());
		sessionFilter.addMappingForUrlPatterns(null, true, "*.ctrl", "/index.jsp");

		ConstUtils.applicationContext = context;
	}

	private AnnotationConfigWebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("org.kyll.myserver.appconfig");
		return context;
	}
}
