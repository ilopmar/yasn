package yasn.notification

import yasn.user.User
import yasn.ro.FollowRequest

class NotificationService {
    static transactional = false

    FollowRequest doNotifyFollower(FollowRequest followRequest) {
        println "---> Notifying ${followRequest.user} his new follower ${followRequest.follower}"

        // Send the email. No matter how it takes because this method is called asynchronous with SI
        followRequest
    }
}