package yasn.notification

import yasn.user.User
import yasn.ro.FollowRequest

class NotificationService {
    static transactional = false

    FollowRequest doNotifyFollower(FollowRequest followRequest) {
        sleep 3000
        println "Notifying ${followRequest.user} his new follower ${followRequest.follower}"

        followRequest
    }

}