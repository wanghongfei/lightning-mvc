package cn.fh.lightning.mvc;

import java.util.Map;

/**
 * This interface is exposed to framework designer
 */
public interface InternalModel extends Model {
	public Map<String, Object> getAttrMap();
}
