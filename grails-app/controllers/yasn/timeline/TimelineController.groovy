package yasn.timeline

import grails.converters.JSON

import grails.plugin.springsecurity.annotation.Secured

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
class TimelineController {

    def timelineService
    def springSecurityService

    public publish(String content) {
        def tl = timelineService.addTimeline(springSecurityService.currentUser, content)

        render tl as JSON
    }
}