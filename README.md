# JTeleBot
[![Build Status](https://travis-ci.org/shibme/jtelebot.svg)](https://travis-ci.org/shibme/jtelebot)
[![Dependency Status](https://www.versioneye.com/user/projects/56adffaa7e03c7003ba414dd/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56adffaa7e03c7003ba414dd)
[![Download](https://api.bintray.com/packages/shibme/maven/jtelebot/images/download.svg)](https://bintray.com/shibme/maven/jtelebot/_latestVersion)
[![Percentage of issues still open](http://isitmaintained.com/badge/open/shibme/jtelebot.svg)](http://isitmaintained.com/project/shibme/jtelebot "Percentage of issues still open")

A Java library for [Telegram Bot API](https://core.telegram.org/bots/api)

### Maven Dependency for Consumers
Add to your `pom.xml`
```xml
<dependency>
	<groupId>me.shib.java.lib</groupId>
	<artifactId>jtelebot</artifactId>
	<version>2.1.0</version>
</dependency>
```

### Example
Start by creating an instance of the `me.shib.java.lib.jtelebot.TelegramBot` with the API token you get from [@BotFather](https://telegram.me/BotFather)
```java
TelegramBot bot = TelegramBot.getInstance("YourBotApiTokenGoesHere");
Update[] updates;
while((updates = bot.getUpdates()) != null) {
    for (Update update : updates) {
        Message message = update.getMessage();
        if(message != null) {
            bot.sendMessage(new ChatId(message.getChat().getId()), "This is a reply from the bot! :)");
        }
    }
}
```
