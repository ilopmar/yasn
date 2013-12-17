package yasn.pipeline

import yasn.ro.FollowRequest

interface Follower {
    def follow(FollowRequest followRequest)
}