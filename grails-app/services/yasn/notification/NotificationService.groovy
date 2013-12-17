package yasn.notification

import yasn.user.User
import yasn.ro.FollowRequest
import yasn.ro.PublishRequest

class NotificationService {
    static transactional = false

    FollowRequest doNotifyFollower(FollowRequest followRequest) {
        sleep 3000
        println "Notifying ${followRequest.user} his new follower ${followRequest.follower}"

        followRequest
    }

    PublishRequest doUpdateFollowersTimeline(PublishRequest publishRequest) {
        sleep 2000
        println "Updating ${publishRequest.user} followers' timelines"

        publishRequest
    }

}