package yasn.relation

import spock.lang.*

import yasn.ro.FollowRequest
import yasn.ro.PublishRequest
import yasn.user.User
import yasn.timeline.Timeline
import yasn.timeline.TimelineService
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

    @Unroll
    void 'count the followings of a user when the user followings is #n'() {
        setup:
            def user = User.build()
            n.times {
                def anotherUser = User.build()
                FollowRelation.build(follower: user, user: anotherUser)
            }

        when:
            def followingsCount = relationService.countFollowings(user)

        then:
            followingsCount == n

        where:
            n << [0, 5]
    }

    @Unroll
    void 'count the followers of a user when the user followers in #n'() {
        setup:
            def user = User.build()

        and: 'mock collaborator'
            def yasnRedisService = Stub(YasnRedisService)
            relationService.yasnRedisService = yasnRedisService
            yasnRedisService.countFollowers(user) >> n

        when:
            def followersCount = relationService.countFollowers(user)

        then:
            followersCount == n

        where:
            n << [0, 5]
    }

    void 'notify all the common followers that there is a new timeline '() {
        setup:
            def user = User.build()
            def timeline = Timeline.build(user: user, content: text)
            def publishRequest = new PublishRequest(user: user, timeline: timeline)

        and: 'mock collaborator'
            def mentionedUser = User.build(username: "johndoe")
            def timelineService = Stub(TimelineService)
            relationService.timelineService = timelineService
            timelineService.isTimelineForAFollower(publishRequest) >> mentionedUser

        and: 'mock collaborator'
            def yasnRedisService = Mock(YasnRedisService)
            relationService.yasnRedisService = yasnRedisService

        when:
            relationService.doGetFollowersToNotify(publishRequest)

        then:
            1 * yasnRedisService.commonFollowersWithUser(user, mentionedUser)

        where:
            text = "@johndoe the text"
    }

    void 'notify all the followers that there is a new timeline '() {
        setup:
            def user = User.build()
            def timeline = Timeline.build(user: user, content: text)
            def publishRequest = new PublishRequest(user: user, timeline: timeline)

        and: 'mock collaborator'
            def timelineService = Stub(TimelineService)
            relationService.timelineService = timelineService
            timelineService.isTimelineForAFollower(publishRequest) >> null

        and: 'mock collaborator'
            def yasnRedisService = Mock(YasnRedisService)
            relationService.yasnRedisService = yasnRedisService

        when:
            relationService.doGetFollowersToNotify(publishRequest)

        then:
            1 * yasnRedisService.followerIds(user)

        where:
            text = "the text"
    }
}