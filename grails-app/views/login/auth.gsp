<html>
<head>
    <meta name='layout' content='main'/>
    <title>Yet Another Social Network</title>
</head>

<body>
    <header>
        <g:render template="/common/header" model="[user: currentUser]" />
    </header>
    <div id="content">
        <div class="wrapper">
            <img src="${resource(dir:'gfx', file:'image-home.jpg')}">
            <div class="panel right">
                <h1>Login</h1>
                <p>
                    <form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
                        <input placeholder="username" name="j_username" type="text">
                        <input placeholder="password" name="j_password" type="password">
                        <input name="password2" style="visibility:hidden;">
                        <input type='checkbox' name='${rememberMeParameter}' id='remember_me' checked='checked' style="display:none;"/>
                        <input type="submit" value="Login">
                    </form>
                </p>
            </div>
        </div>
    </div>
    <footer>
        <g:render template="/common/footer" />
    </footer>
</body>
</html>
