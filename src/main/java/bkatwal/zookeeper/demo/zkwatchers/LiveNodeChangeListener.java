package bkatwal.zookeeper.demo.zkwatchers;

import bkatwal.zookeeper.demo.LogExample;
import bkatwal.zookeeper.demo.util.ClusterInfo;
import java.util.List;
import org.I0Itec.zkclient.IZkChildListener;

/** @author "Bikas Katwal" 26/03/19 */

public class LiveNodeChangeListener implements IZkChildListener {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class);
  /**
   * - This method will be invoked for any change in /live_nodes children
   * - During registering this listener make sure you register with path /live_nodes
   * - after receiving notification it will update the local clusterInfo object
   *
   * @param parentPath this will be passed as /live_nodes
   * @param currentChildren new list of children that are present in /live_nodes, children's string alue is znode name which is set as server hostname
   */
  @Override
  public void handleChildChange(String parentPath, List<String> currentChildren) {
    log.info("current live size: {}", currentChildren.size());
    ClusterInfo.getClusterInfo().getLiveNodes().clear();
    ClusterInfo.getClusterInfo().getLiveNodes().addAll(currentChildren);
  }
}
