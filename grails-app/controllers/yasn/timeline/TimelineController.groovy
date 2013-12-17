package yasn.timeline

import grails.converters.JSON

import grails.plugin.springsecurity.annotation.Secured

import yasn.ro.PublishRequest

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
class TimelineController {

    def springSecurityService
    def publishPipeline

    public publish(String content) {
        def publishRequest = new PublishRequest(
            user: springSecurityService.currentUser,
            content: content
        )

        publishPipeline.publish(publishRequest)

        render publishRequest.timeline as JSON
    }
}