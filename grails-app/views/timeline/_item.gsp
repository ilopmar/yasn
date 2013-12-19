<g:set var="user" value="${timeline.user}" />

<div class="ribbitWrapper" <g:if test="${hidden}">style='display:none;'</g:if>>
    <g:render template="/user/avatar" model="[user: user]" />

    <span class="name">${user.name}</span>
    <span class="username">@${user.username}</span>
    <span class="time"><prettytime:display date="${timeline.dateCreated}" /></span>

    <p><yasn:parseTextToLink text="${timeline.content}" /></p>
</div>