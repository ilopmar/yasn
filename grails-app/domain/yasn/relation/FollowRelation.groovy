package yasn.relation

import yasn.user.User

class FollowRelation {

    // The 'follower' follows 'user'
    User follower
    User user

    Date dateCreated

    static constraints = {
        // A user can not have any relationship with himself
        user validator: {val, obj ->
            val.id != obj.follower.id
        }
    }

    static mapping = {
    }
}
