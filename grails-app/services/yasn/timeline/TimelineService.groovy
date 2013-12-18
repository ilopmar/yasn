package yasn.timeline

import yasn.user.User
import yasn.ro.PublishRequest

class TimelineService {

    private static final Integer ITEMS_BY_PAGE = 10

    def yasnRedisService

    Timeline addTimeline(User user, String content) {
        def tl = new Timeline(user: user, content: content)
        tl.save()

        tl
    }

    PublishRequest doAddTimeline(PublishRequest publishRequest) {
        publishRequest.timeline = this.addTimeline(publishRequest.user, publishRequest.content)

        publishRequest
    }

    PublishRequest doUpdateFollowersTimeline(PublishRequest publishRequest) {
        println "---> Updating ${publishRequest.user} followers' timelines"

        yasnRedisService.updateTimeline(publishRequest.user, publishRequest.timeline)

        publishRequest
    }

    List<Timeline> timeline(User user, Integer page = 0) {
        def start = page * ITEMS_BY_PAGE
        def end = start + ITEMS_BY_PAGE - 1

        def redisTl = yasnRedisService.timeline(user.id, start, end)

        def timeline = Timeline.withCriteria {
            'in' 'id', redisTl.collect { Long.valueOf(it) }
        }

        return timeline
    }

}