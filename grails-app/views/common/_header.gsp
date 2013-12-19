<div class="wrapper">
    <img src="${resource(dir:'gfx', file:'logo.png')}">
    <span>Yet Another Social Network: <span class="small">An Uberfast Social Network</span></span>
    <sec:ifLoggedIn>
        <g:form mapping='logoutIndex' method="POST">
            <a id="btnLogOut" class="btn" href="${createLink(mapping:'logoutIndex')}">Logout</a>
        </g:form>
    </sec:ifLoggedIn>
</div>