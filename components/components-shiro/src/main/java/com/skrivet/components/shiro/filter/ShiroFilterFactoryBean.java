package com.skrivet.components.shiro.filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.ExecutionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.BeanInitializationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * 自定义shiro过滤器代理类，实现url动态数据库加载
 *
 * @author PolarLoves
 *
 */
public class ShiroFilterFactoryBean extends org.apache.shiro.spring.web.ShiroFilterFactoryBean {

	@Override
	protected FilterChainManager createFilterChainManager() {
		FilterChainManager result=super.createFilterChainManager();
		//databaseResouceFilter.loadDataBasePermissions();
		return result;
	}

	public Class getObjectType() {
		return FilterStaticResourceSpringShiroFilter.class;
	}
	@Override
	protected AbstractShiroFilter createInstance() throws Exception {
		SecurityManager securityManager = this.getSecurityManager();
		String msg;
		if (securityManager == null) {
			msg = "SecurityManager property must be set.";
			throw new BeanInitializationException(msg);
		} else if (!(securityManager instanceof WebSecurityManager)) {
			msg = "The security manager does not implement the WebSecurityManager interface.";
			throw new BeanInitializationException(msg);
		} else {
			FilterChainManager manager = this.createFilterChainManager();
			PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
			chainResolver.setFilterChainManager(manager);
			return new FilterStaticResourceSpringShiroFilter((WebSecurityManager)securityManager, chainResolver);
		}

	}
	private static final class FilterStaticResourceSpringShiroFilter extends AbstractShiroFilter {
		protected FilterStaticResourceSpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver) {
			if (webSecurityManager == null) {
				throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
			} else {
				this.setSecurityManager(webSecurityManager);
				if (resolver != null) {
					this.setFilterChainResolver(resolver);
				}
			}
		}
		private static PatternMatcher staticResourcePathMatcher = new AntPathMatcher();
		private static final String staticResourceAnt = "/static/**";
		@Override
		protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, final FilterChain chain) throws ServletException, IOException {
			Throwable t=null;
			try {
				final ServletRequest request = this.prepareServletRequest(servletRequest, servletResponse, chain);
				final ServletResponse response = this.prepareServletResponse(request, servletResponse, chain);
				String requestURI = WebUtils.getPathWithinApplication((HttpServletRequest) request);
				if(staticResourcePathMatcher.matches(staticResourceAnt,requestURI) ){
					chain.doFilter(request,response);
				}else{
					Subject subject = this.createSubject(request, response);
					subject.execute(new Callable() {
						public Object call() throws Exception {
							FilterStaticResourceSpringShiroFilter.this.updateSessionLastAccessTime(request, response);
							FilterStaticResourceSpringShiroFilter.this.executeChain(request, response, chain);
							return null;
						}
					});
				}
			} catch (ExecutionException var8) {
				t = var8.getCause();
			} catch (Throwable var9) {
				t = var9;
			}

			if (t != null) {
				if (t instanceof ServletException) {
					throw (ServletException)t;
				} else if (t instanceof IOException) {
					throw (IOException)t;
				} else {
					String msg = "Filtered request failed.";
					throw new ServletException(msg, t);
				}
			}
		}
	}

}