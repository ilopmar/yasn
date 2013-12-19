package yasn.relation

import yasn.user.User
import yasn.ro.FollowRequest

class RelationService {
    def yasnRedisService

    void addFollower(User follower, User user) {
        FollowRelation.findOrSaveByFollowerAndUser(follower, user)
    }

    Boolean isFollower(User follower, User user) {
        // return FollowRelation.countByFollowerAndUser(follower, user) > 0
        return yasnRedisService.isFollower(follower, user)
    }

    FollowRequest doAddFollower(FollowRequest followRequest) {
        this.addFollower(followRequest.follower, followRequest.user)

        followRequest
    }

    void doUpdateFollowers(FollowRequest followRequest) {
        println "Updating followers of ${followRequest.user} with ${followRequest.follower}"

        yasnRedisService.addFollower(followRequest.follower, followRequest.user)
    }

    Integer countFollowers(User user) {
        return FollowRelation.countByUser(user)
    }

    Integer countFollowings(User user) {
        return FollowRelation.countByFollower(user)
    }
}