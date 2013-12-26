package yasn.relation

import yasn.user.User
import yasn.ro.FollowRequest
import yasn.ro.PublishRequest
import yasn.timeline.Timeline

class RelationService {
    def yasnRedisService
    def timelineService

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
        log.info "Updating followers of ${followRequest.user} with ${followRequest.follower}"

        yasnRedisService.addFollower(followRequest.follower, followRequest.user)
    }

    PublishRequest doGetFollowersToNotify(PublishRequest publishRequest) {
        log.info "Getting followers to notify"

        def user = publishRequest.user
        def timeline = publishRequest.timeline

        def mentionedUser = timelineService.isTimelineForAFollower(publishRequest)
        if (mentionedUser) {
            // Get only common followers
            publishRequest.followers = yasnRedisService.commonFollowersWithUser(user, mentionedUser)
        } else {
            // Get all followers
            publishRequest.followers = yasnRedisService.followerIds(user)
        }

        publishRequest
    }

    Integer countFollowers(User user) {
        return yasnRedisService.countFollowers(user)
    }

    Integer countFollowings(User user) {
        return FollowRelation.countByFollower(user)
    }

}