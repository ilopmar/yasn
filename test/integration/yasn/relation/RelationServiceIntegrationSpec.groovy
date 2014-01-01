package yasn.relation

import spock.lang.*

import yasn.ro.FollowRequest
import yasn.user.User
import yasn.YasnRedisService

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

    void 'add a follower with the data of the follower flow'() {
        setup:
            def user = User.build()
            def follower = User.build()
            def followRequest = new FollowRequest(follower: follower, user: user)

        when:
            relationService.doAddFollower(followRequest)

        then:
            FollowRelation.countByFollowerAndUser(follower, user) == 1
    }

    void 'update the followers with the data of the follower flow'() {
        setup:
            def user = User.build()
            def follower = User.build()
            def followRequest = new FollowRequest(follower: follower, user: user)

        and: 'mock collaborator'
            def yasnRedisService = Mock(YasnRedisService)
            relationService.yasnRedisService = yasnRedisService

        when:
            relationService.doUpdateFollowers(followRequest)

        then:
            1 * yasnRedisService.addFollower(follower, user)
    }

    @Unroll
    void 'check if a user is following another one'() {
        setup:
            def user = User.build()
            def follower = User.build()

        and: 'mock collaborator'
            def yasnRedisService = Stub(YasnRedisService)
            relationService.yasnRedisService = yasnRedisService
            yasnRedisService.isFollower(follower, user) >> isFollower

        when:
            def result = relationService.isFollower(follower, user)

        then:
            result == isFollower

        where:
            isFollower << [true, false]
    }
}