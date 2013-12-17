package yasn

import yasn.user.User

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

    Set followerIds(User user) {
        return redisService.smembers("followers.${user.id}")
    }
}
