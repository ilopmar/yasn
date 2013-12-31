# Yet Another Social Network

![logo](https://raw.github.com/lmivan/yasn/master/web-app/gfx/logo.png)

YASN is a Twitter-like clone developed as a proof-of-concept to create an uberfast social network.

The idea is that in the _big ones_ social networks like Instagram the ratio between the reads and the writes is far too long for the reads. With millions of users the reads must be really fast and it's acceptable that the writes takes a few milliseconds to propagate the changes to all the followers.

This application uses Redis as a secondary fast storage to achieve this. Every user in the systems has a list in Redis with his timeline sorted and a set with his followers. When a user publish a new message it's stored synchronously in the relational database and after that a new asynchronous task is created to propagate this message to all of his followers. The flow of the information is done with Spring Integration.

This project has been developed during the 5th [Kaleidos](http://kaleidos.net) [Piweek](http://piweek.tumblr.com)

![home](https://raw.github.com/lmivan/yasn/master/screenshot.png)

## Demo

You need `Redis` to run the application. Once the application has started, connect to `http://localhost:8080/yasn`.

During the bootstrap process the application creates some users with some random data. There are two users with 1.000.000 followers: `justinbieber` and `hannahmontana` and another user with 300 followers: `johndoe`. The rest of the users are created with the pattern `user0` , `user1`,... The password for all the users is `pass`.

When you publish a message with a user you can see in the console the progress of the propagation of this message to all the followers. For the user `johndoe` it takes approximately 20 ms in my laptop. For `justinbieber` or `hannahmontana` it takes about 3 seconds.

Another feature is that when you start a message mentioning a user with `@username` the message it's only propagated to the followers that both users (the creator and the mentioned) have in common. For example, if you create a message with `johndoe`, `justinbieber` doesn't see it in his timeline. But if `johndoe` creates a message starting with `@justinbieber` then Justin can see it.

The mention feature inside the message has not been implemented.

## Architecture

Another architectural difference between this application and a "standard" grails application is that the flow has been modeled and created with Spring Integration. This way we can change the behaviour of the application changing the configuration instead of the code. The different parts are isolated and do not know anything about the rest of code. They are isolated and easily testeable.

Check the configuration files in `grails-app/conf/spring/` directory.

## Author

If you have some comments, please send me an email at lopez.ivan@gmail.com. You can also contact with me by twitter at [@ilopmar](https://twitter.com/ilopmar).














[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/lmivan/yasn/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

