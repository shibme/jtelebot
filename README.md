# JTeleBot
A Java library for [Telegram Bot API](https://core.telegram.org/bots/api)

### Build Status
[![Build Status](https://travis-ci.org/shibme/jtelebot.svg)](https://travis-ci.org/shibme/jtelebot)

### Maven Dependency for Consumers
Add to your `pom.xml`:

```xml
<dependency>
	<groupId>me.shib.java.lib</groupId>
	<artifactId>jtelebot</artifactId>
	<version>0.8.3</version>
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