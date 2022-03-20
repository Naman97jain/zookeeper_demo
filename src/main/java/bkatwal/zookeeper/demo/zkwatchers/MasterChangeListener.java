package bkatwal.zookeeper.demo.zkwatchers;

import bkatwal.zookeeper.demo.api.ZkService;
import bkatwal.zookeeper.demo.LogExample;
import bkatwal.zookeeper.demo.util.ClusterInfo;
import java.util.List;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

/** @author "Bikas Katwal" 27/03/19 */

public class MasterChangeListener implements IZkChildListener {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class);
	private ZkService zkService;

	/**
	 * listens for creation/deletion of znode "master" under /election znode and
	 * updates the clusterinfo
	 *
	 * @param parentPath
	 * @param currentChildren
	 */
	@Override
	public void handleChildChange(String parentPath, List<String> currentChildren) {
		if (currentChildren.isEmpty()) {
			log.info("master deleted, recreating master!");
			ClusterInfo.getClusterInfo().setMaster(null);
			try {

				zkService.electForMaster();
			} catch (ZkNodeExistsException e) {
				log.info("master already created");
			}
		} else {
			String leaderNode = zkService.getLeaderNodeData();
			log.info("updating new master: {}", leaderNode);
			ClusterInfo.getClusterInfo().setMaster(leaderNode);
		}
	}

	public ZkService getZkService() {
		return zkService;
	}

	public void setZkService(ZkService zkService) {
		this.zkService = zkService;
	}
}
