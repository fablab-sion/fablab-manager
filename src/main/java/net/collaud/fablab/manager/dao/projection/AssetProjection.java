package net.collaud.fablab.manager.dao.projection;

import com.mysema.query.types.Expression;
import net.collaud.fablab.manager.data.AssetEO;
import net.collaud.fablab.manager.data.QAssetEO;

/**
 *
 * @author Gaetan Collaud
 */
public class AssetProjection {
	
	public static final Expression<AssetEO> projectionWithoutContent(QAssetEO asset){
		return QAssetEO.create(asset.id, asset.title, asset.mime, UserProjections.getSimpleUser(asset.owner));
	}
	public static final Expression<AssetEO> projectionWithContent(QAssetEO asset){
		return QAssetEO.create(asset.id, asset.title, asset.data, asset.mime, UserProjections.getSimpleUser(asset.owner));
	}
}
