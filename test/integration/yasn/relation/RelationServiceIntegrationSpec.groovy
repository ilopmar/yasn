package yasn.relation

import spock.lang.*

import yasn.user.User

class RelationServiceIntegrationSpec extends Specification {

    def relationService

    void 'add a new follower relation'() {
        setup:
            def user = User.build()
            def follower = User.build()

        when:
            relationService.addFollower(follower, user)

        then:
            FollowRelation.countByFollowerAndUser(follower, user) == 1
    }

    void 'try to add a new follower relation when a relation already exists'() {
        setup:
            def user = User.build()
            def follower = User.build()
            FollowRelation.build(follower: follower, user: user)

        when:
            relationService.addFollower(follower, user)

        then:
            FollowRelation.countByFollowerAndUser(follower, user) == 1
    }

}