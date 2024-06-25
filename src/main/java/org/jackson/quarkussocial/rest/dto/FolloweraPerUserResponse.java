package org.jackson.quarkussocial.rest.dto;

import java.util.List;

public class FolloweraPerUserResponse {
    private Integer followersCount;
    private List<FollowerResponse> content;

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public List<FollowerResponse> getContente() {
        return content;
    }

    public void setContent(List<FollowerResponse> content) {
        this.content = content;
    }
}
