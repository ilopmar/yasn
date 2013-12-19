<h1>Your Yasn profile</h1>
<div class="ribbitWrapper">
    <g:render template="/user/avatar" model="[user: user]" />
    <span class="name">${user.name}</span> @${user.username}
    <p>
        567 Messages<span class="spacing">${user.countFollowers()} Followers</span><span class="spacing">${user.countFollowings()} Following</span><br>
        ${user.bio}
    </p>
</div>