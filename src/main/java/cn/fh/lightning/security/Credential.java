package cn.fh.lightning.security;

import java.util.List;

public interface Credential {
	String getUsername();
	String getNickName();

	List<String> getRoleList();
	boolean hasRole(String roleName);
	
	void addRole(String roleName);
	void addRoles(String[] roleNames);
	
	/**
	 * 实现类的对象在ServletContext中的key名
	 */
	String CREDENTIAL_CONTEXT_ATTRIBUTE = "CURRENT_USER_CREDENTIAL";
}
