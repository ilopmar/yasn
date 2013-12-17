package yasn.relation

import yasn.user.User
import yasn.ro.FollowRequest

class RelationService {

    void addFollower(User follower, User user) {
        FollowRelation.findOrSaveByFollowerAndUser(follower, user)
    }

    Boolean isFollower(User follower, User User) {
        return FollowRelation.countByFollowerAndUser(follower, user) > 0
    }

    FollowRequest doAddFollower(FollowRequest followRequest) {
        this.addFollower(followRequest.follower, followRequest.user)

        followRequest
    }

    FollowRequest doUpdateFollowers(FollowRequest followRequest) {
        sleep 3000
        println "Updating followers of ${followRequest.user} with ${followRequest.follower}"

        followRequest
    }
}