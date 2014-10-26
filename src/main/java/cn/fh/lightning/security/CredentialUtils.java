package cn.fh.lightning.security;

import javax.servlet.http.HttpSession;

public class CredentialUtils {
	
	/**
	 * 从指定session中得到Credential
	 * @param session 当前session
	 * @return 如果用户未登陆,返回null
	 */
	public static Credential getCredential(HttpSession session) {
		Credential credential = (Credential) session.getAttribute(Credential.CREDENTIAL_CONTEXT_ATTRIBUTE);
		
		return credential;
	}
	
	/**
	 * 将用户Credential放入当前session中去.
	 * <p> 如果当前session中存在一个Credential，则扔RuntimeException
	 * @param session 当前session
	 * @param credential
	 * @return
	 */
	public static void createCredential(HttpSession session, Credential credential) {
		if (null != session.getAttribute(Credential.CREDENTIAL_CONTEXT_ATTRIBUTE)) {
			throw new RuntimeException("用户已登陆，不得重复设置Credential!");
		}

		session.setAttribute(Credential.CREDENTIAL_CONTEXT_ATTRIBUTE, credential);
	}
}
