package com.techgenii.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

@Slf4j
public class LeaderElection implements Watcher {

    private static final String ZOOKEEPER_ADDRESS = "localhost:2181";
    private static final int SESSION_TIMEOUT = 3000;
    private ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException {
        final LeaderElection leaderElection = new LeaderElection();
        leaderElection.connectToZookeeper();
        leaderElection.waitForZookeeper();
        leaderElection.close();
        log.info("Disconnected from Zookeeper, exiting application");
    }

    public void waitForZookeeper() throws InterruptedException {
        synchronized (zooKeeper) {
            zooKeeper.wait();
        }
    }

    public void close() throws InterruptedException {
        zooKeeper.close();
    }

    public void connectToZookeeper() throws IOException {
        this.zooKeeper = new ZooKeeper(ZOOKEEPER_ADDRESS, SESSION_TIMEOUT, this);
    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()) {

            case None:
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    log.info("Successfully connected to Zookeeper");
                } else {
                    synchronized (zooKeeper) {
                        log.info("Disconnected from zookeeper event");
                        zooKeeper.notifyAll();
                    }
                }
                break;
            case NodeCreated:
                break;
            case NodeDeleted:
                break;
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
            case DataWatchRemoved:
                break;
            case ChildWatchRemoved:
                break;
            case PersistentWatchRemoved:
                break;
        }
    }
}
