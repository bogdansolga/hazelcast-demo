package com.sg.hazelcast.demo.presentation;

import com.hazelcast.cluster.ClusterState;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(path = "/hz")
public class HazelcastStatsController {

    private final HazelcastInstance hazelcastInstance;

    @Autowired
    public HazelcastStatsController(final HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @GetMapping(path = "/members")
    public Set<Member> getClusterMembers() {
        return hazelcastInstance.getCluster().getMembers();
    }

    @GetMapping(path = "/state")
    public ClusterState clusterState() {
        return hazelcastInstance.getCluster().getClusterState();
    }
}
