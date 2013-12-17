package yasn.timeline

import yasn.user.User
import yasn.ro.PublishRequest

class TimelineService {

    Timeline addTimeline(User user, String content) {
        def tl = new Timeline(user: user, content: content)
        tl.save()

        tl
    }

    PublishRequest doAddTimeline(PublishRequest publishRequest) {
        publishRequest.timeline = this.addTimeline(publishRequest.user, publishRequest.content)

        publishRequest
    }
}