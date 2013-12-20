package yasn

import yasn.user.User
import yasn.timeline.Timeline

class YasnRedisService {
    static transactional = false

    def redisService

    void addFollower(User follower, User user) {
        if (user.id != follower.id) {
            redisService.sadd "followers.${user.id}", follower.id.toString()
        }
    }

    Boolean isFollower(User follower, User user) {
        return redisService.sismember("followers.${follower.id}", user.id.toString())
    }

    void updateTimeline(Set followers, Timeline timeline) {
        def t = System.nanoTime()

        println "Updating the timeline of ===${followers.size()}=== followers in Redis"

        String timelineId = timeline.id.toString()

        redisService.withPipeline { pipeline ->
            // Add to user timeline
            pipeline.lpush("timeline.${timeline.user.id}", timelineId)

            // Add timeline object to timeline followers
            followers.eachWithIndex { followerId, idx ->
                if (idx % 10000 == 0) {
                    println "Updating followers' timelines. Progress: ${idx}"
                }
                pipeline.lpush("timeline.${followerId}", timelineId)
            }
        }

        def t2 = System.nanoTime()
        println "Time --> " + ((t2 - t)/1000000)
    }

    public Set followerIds(User user) {
        println "Getting followers of ${user}"
        return redisService.smembers("followers.${user.id}")
    }

    List timeline(Long userId, Integer start, Integer stop) {
        redisService.lrange("timeline.${userId}", start, stop)
    }

    private Boolean isForAFollower(User user, Timeline timeline) {
        return false
    }

    Set commonFollowers(User userA, User userB) {
        return redisService.sinter("followers.${userA.id}", "followers.${userB.id}")
    }

    Set commonFollowersWithUser(User userA, User userB) {
        def commonFollowers = this.commonFollowers(userA, userB)

        // Add the mentioned user
        commonFollowers.addAll(userB.id.toString())

        return commonFollowers
    }

    Integer countFollowers(User user) {
        return redisService.scard("followers.${user.id}")
    }

}
