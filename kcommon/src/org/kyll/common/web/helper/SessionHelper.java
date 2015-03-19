package org.kyll.common.web.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class SessionHelper {
	private HttpSession session;

	public static SessionHelper getSessionHelper(HttpServletRequest request) {
		return SessionHelper.getSessionHelper(request, true);
	}

	public static SessionHelper getSessionHelper(HttpServletRequest request, boolean isCreate) {
		return new SessionHelper(request.getSession(isCreate));
	}

	public void setAttribute(String name, Object value) {
		session.setAttribute(name, value);
	}

	public Object getAttribute(String name) {
		return session.getAttribute(name);
	}

	public void removeAttribute(String name) {
		session.removeAttribute(name);
	}

	public <T extends SessionUser> T getCurrentUser(T t) {
		t.setId((String) this.getAttribute(SessionUser.SESSION_USER_ID));
		return t;
	}

	public static interface SessionUser {
		String SESSION_USER_ID = "user_id";

		void setId(String id);
	}

	private SessionHelper(HttpSession session) {
		this.session = session;
	}
}
