package yasn.relation

import yasn.user.User

class RelationService {

    void addFollower(User user, User follower) {
        def relation = new FollowRelation(user: user, follower: follower)
        relation.save()
    }

    Boolean isFollower(User user, User follower) {
        return FollowRelation.countByUserAndFollower(user, follower) > 0
    }
}