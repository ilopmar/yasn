package yasn.timeline

import yasn.user.User

class TimelineService {

    Timeline addTimeline(User user, String content) {
        def tl = new Timeline(user: user, content: content)
        tl.save()

        tl
    }
}