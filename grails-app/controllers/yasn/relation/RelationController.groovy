package yasn.relation

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import yasn.ro.FollowRequest
import yasn.user.User

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
class RelationController {

    def followerPipeline
    def springSecurityService

    public addFollower(User user) {

        def followRequest = new FollowRequest(
            follower: springSecurityService.currentUser,
            user: user
        )

        followerPipeline.follow(followRequest)

        render "Done!"
    }
}