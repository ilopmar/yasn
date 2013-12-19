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
                    <form id="publish-form" action="${createLink(mapping:'publish')}">
                        <textarea id="text-content" name="content" class="ribbitText"></textarea>
                        <input type="submit" value="Publish!">
                    </form>
                </p>
            </div>

            <div id="ribbits" class="panel left">
                <g:render template="/user/profile" model="[user: currentUser]" />
            </div>

            <div class="panel left">
                <h1>Your Yasn Buddies</h1>
                <div id="timeline-wrapper" data-url="${createLink(mapping:'timeline')}">
                    <g:render template="/timeline/timeline" model="[timeline: timeline]" />
                </div>

                <div id="infinite-scroll" />
            </div>
        </div>
    </div>
    <footer>
        <g:render template="/common/footer" />
    </footer>
</body>
</html>
