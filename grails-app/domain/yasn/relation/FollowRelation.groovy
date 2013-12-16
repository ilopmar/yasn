package yasn.relation

import yasn.user.User

class FollowRelation {

    User user
    User follower

    Date dateCreated
    Date lastUpdated

    static constraints = {
        // A user can not have any relationship with himself
        user validator: {val, obj ->
            val.id != obj.follower.id
        }
    }

    static mapping = {
    }
}
