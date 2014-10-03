package cn.fh.lightning.mvc;

import java.util.HashMap;
import java.util.Map;

public class BasicModel implements Model, InternalModel {
	private Map<String, Object> attrMap = new HashMap<String, Object>();

	@Override
	public void addAttribute(String name, Object attr) {
		this.attrMap.put(name, attr);
	}

	@Override
	public Map<String, Object> getAttrMap() {
		return this.attrMap;
	}

}
