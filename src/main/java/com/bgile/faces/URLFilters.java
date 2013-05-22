
package com.bgile.faces;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author PeerapatAsoktummarun
 */
public class URLFilters implements Filter {

	private static final Logger LOG = Logger.getLogger(URLFilters.class.getName());

	private FilterConfig config;

	/**
	 *
	 * @param request The servlet request we are processing
	 * @param response The servlet response we are creating
	 * @param chain The filter chain we are processing
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet error occurs
	 */
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response,
			final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest httpServletRequest = ((HttpServletRequest)request);
		final String servletpath = httpServletRequest.getServletPath();

		LOG.log(Level.INFO, "Servletpath: {0}", servletpath);

		if (servletpath.contains(".xhtml")) {
			chain.doFilter(request, response);
		} else {
			final List<String> params = new LinkedList<>();
			for (final String param: servletpath.split("/")) {
				if( !param.trim().equals("") ) {
					params.add(param);
				}
			}
			request.setAttribute("PARAMS", params);
			httpServletRequest.getRequestDispatcher(genUrl(params.get(0))).forward(request, response);
		}

	}

	private String genUrl(final String viewid) {
		return "/"+viewid+config.getInitParameter("postfix");
	}

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}

	@Override
	public void destroy() {

	}

}
