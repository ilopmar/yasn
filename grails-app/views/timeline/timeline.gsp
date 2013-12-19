<!DOCTYPE html>
<html>
<head>
    <meta name='layout' content='main'/>
</head>
<body>
    <header>
        <g:render template="/common/header" model="[user: currentUser]" />
    </header>
    <div id="content">
        <div class="wrapper">
            <div id="createRibbit" class="panel right">
                <h1>Publish some content</h1>
                <p>
                    <form>
                        <textarea name="text" class="ribbitText"></textarea>
                        <input type="submit" value="Ribbit!">
                    </form>
                </p>
            </div>

            <div id="ribbits" class="panel left">
                <g:render template="/user/profile" model="[user: currentUser]" />
            </div>

            <div class="panel left">
                <h1>Your Yasn Buddies</h1>
                <g:each in="${timeline}" var="item">
                    <g:render template="/timeline/item" model="[timeline: item]" />
                </g:each>
            </div>
        </div>
    </div>
    <footer>
        <g:render template="/common/footer" />
    </footer>
</body>
</html>
