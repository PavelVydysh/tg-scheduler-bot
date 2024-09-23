package org.example.infrastructure.configuration;

public class Environment {
    public static final String BOT_PROPERTIES_PREFIX = "telegram.bot";
    public static final String COMMAND_STATUS_MESSAGE_KEY = "${commands.status.message}";
    public static final String COMMAND_INFO_MESSAGE_KEY = "${commands.info.message}";
    public static final String COMMAND_POLL_IS_ANONYMOUS_KEY = "${commands.poll.is-anonymous}";
    public static final String COMMAND_POLL_TITLE = "${commands.poll.title}";
}
