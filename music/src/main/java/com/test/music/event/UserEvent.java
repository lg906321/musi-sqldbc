package com.test.music.event;

import com.test.music.common.ParamUtil;

public final class UserEvent {

    public final static String USER_PAGE_LIST = ParamUtil.eventFormat(UserEvent.class, "USER_PAGE_LIST");

    public final static String QUERY_USER_LIST = ParamUtil.eventFormat(UserEvent.class, "queryUserList");
}