package yasn.timeline

import grails.converters.JSON

import grails.plugin.springsecurity.annotation.Secured

import yasn.ro.PublishRequest

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
class TimelineController {

    def springSecurityService
    def publishPipeline
    def timelineService

    public publish(String content) {
        def publishRequest = new PublishRequest(
            user: springSecurityService.currentUser,
            content: content
        )

        publishPipeline.publish(publishRequest)

        render publishRequest.timeline as JSON
    }

    public timeline(Integer page) {
        page = page ?: 0

        def tl = timelineService.timeline(springSecurityService.currentUser, page)

        render tl
    }
}