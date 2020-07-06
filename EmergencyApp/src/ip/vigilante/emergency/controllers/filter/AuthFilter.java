package ip.vigilante.emergency.controllers.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(
		filterName = "AuthFilter", 
		urlPatterns = { "/*" },
		dispatcherTypes = {
			DispatcherType.ERROR, 
			DispatcherType.FORWARD, 
			DispatcherType.INCLUDE, 
			DispatcherType.REQUEST
		})
public class AuthFilter implements Filter {

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest servletReq = (HttpServletRequest)req;
		HttpServletResponse servletResp = (HttpServletResponse)resp;
		HttpSession session = servletReq.getSession(false);
		
		Pattern[] patterns = {
				Pattern.compile(servletReq.getContextPath() + "/(login.jsp|login)"),
				Pattern.compile(servletReq.getContextPath() + "/(register.jsp|register)"),
				Pattern.compile(servletReq.getContextPath() + "/(home.jsp|home)")
		};

		boolean loggedIn = session != null && session.getAttribute("userId") != null;
		
		boolean guestReq = false;
		for(Pattern pattern : patterns) {
			if(pattern.matcher(servletReq.getRequestURI()).matches()) {
				guestReq = true;
				break;
			}
		}
		
		boolean resourceReq = isResourceRequest(servletReq);
		
		if(loggedIn || guestReq || resourceReq) {
			chain.doFilter(servletReq, servletResp);
		} else {
			servletResp.sendRedirect(servletReq.getContextPath() + "/login.jsp");
		}
	}

	private boolean isResourceRequest(HttpServletRequest servletReq) {
		String path = servletReq.getRequestURI();
		return path.endsWith(".css")
				|| path.endsWith(".js")
				|| path.endsWith(".png")
				|| path.endsWith(".ico")
				|| path.endsWith(".jpg")
				|| path.endsWith(".jpeg");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}