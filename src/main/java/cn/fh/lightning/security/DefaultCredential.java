package cn.fh.lightning.security;

import java.util.ArrayList;
import java.util.List;

/**
 * Credential接口的默认实现类
 * @author whf
 *
 */
public class DefaultCredential implements Credential {
	protected List<String> roleList = new ArrayList<String>();
	protected String username;
	protected String nickName;

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getNickName() {
		return this.nickName;
	}
	
	@Override
	public final void addRole(String roleName) {
		this.roleList.add(roleName);
	}
	
	@Override
	public final void addRoles(String[] roleNames) {
		for (String role : roleNames) {
			this.roleList.add(role);
		}
	}

	/**
	 * 得到该用户所有role
	 */
	@Override
	public final List<String> getRoleList() {
		return this.roleList;
	}

	/**
	 * 判断该用户是否具有指定role
	 */
	@Override
	public final boolean hasRole(String roleName) {
		return this.roleList.contains(roleName);
	}

}
