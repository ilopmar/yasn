package yasn.ro

import yasn.user.User
import yasn.timeline.Timeline

class PublishRequest {
    User user
    String content

    // The timeline created in the flow
    Timeline timeline
}