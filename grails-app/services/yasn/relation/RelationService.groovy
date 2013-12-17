package yasn.relation

import yasn.user.User

class RelationService {

    void addFollower(User user, User follower) {
        FollowRelation.findOrSaveByUserAndFollower(user, follower)
    }

    Boolean isFollower(User user, User follower) {
        return FollowRelation.countByUserAndFollower(user, follower) > 0
    }
}