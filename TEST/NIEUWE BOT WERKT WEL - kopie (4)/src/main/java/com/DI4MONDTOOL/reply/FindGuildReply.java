package com.DI4MONDTOOL.reply;

import com.DI4MONDTOOL.request.RequestType;

@SuppressWarnings("unused")
public class FindGuildReply extends AbstractReply {
    private String guild;

    /**
     * @return The ID of the guild that was found, or null if there was no guild by that name
     */
    public String getGuild() {
        return guild;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.FIND_GUILD;
    }

    @Override
    public String toString() {
        return "FindGuildReply{" +
                "guild=" + guild +
                ", super=" + super.toString() + "}";
    }
}
