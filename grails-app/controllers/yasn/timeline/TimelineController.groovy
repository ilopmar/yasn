package yasn.timeline

import grails.converters.JSON

import grails.plugin.springsecurity.annotation.Secured

import yasn.ro.PublishRequest

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
class TimelineController {

    def springSecurityService
    def publishPipeline
    def timelineService

    public home() {
        def timeline = timelineService.timeline(springSecurityService.currentUser, 0)

        render view: '/timeline/timeline', model: [currentUser: springSecurityService.currentUser, timeline: timeline]
    }

    public publish(String content) {
        def publishRequest = new PublishRequest(
            user: springSecurityService.currentUser,
            content: content
        )

        publishPipeline.publish(publishRequest)

        render template: '/timeline/item', model: [timeline: publishRequest.timeline, hidden: true]
    }

    public timeline(Integer page) {
        page = page ?: 0

        def user = springSecurityService.currentUser

        def tl = timelineService.timeline(user, page)

        render template:'/timeline/timeline', model: [user: user, timeline: tl]
    }
}