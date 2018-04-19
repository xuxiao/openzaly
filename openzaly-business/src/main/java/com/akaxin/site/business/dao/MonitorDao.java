package com.akaxin.site.business.dao;

import com.akaxin.site.storage.api.IGroupDao;
import com.akaxin.site.storage.api.IMessageDao;
import com.akaxin.site.storage.api.IUserGroupDao;
import com.akaxin.site.storage.api.IUserProfileDao;
import com.akaxin.site.storage.service.GroupDaoService;
import com.akaxin.site.storage.service.MessageDaoService;
import com.akaxin.site.storage.service.UserGroupDaoService;
import com.akaxin.site.storage.service.UserProfileDaoService;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.DayOfWeek;

public class MonitorDao {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserProfileDao.class);
    private static MonitorDao instance = new MonitorDao();
    private IMessageDao messageDao = new MessageDaoService();
    private IUserProfileDao userProfileDao = new UserProfileDaoService();
    private IUserGroupDao userGroupDao = new UserGroupDaoService();
    private IGroupDao groupDao = new GroupDaoService();


    public static MonitorDao getInstance() {
        return instance;
    }

    //监控查询状态(消息数量)
    public int queryNumMessagePerDay(long time,int day) {
        try {
            int groupMessagePerDay = userGroupDao.queryGroupMessagePerDay(time,day);
            int u2MessagePerDay = messageDao.queryU2MessagePerDay(time,day);
            return groupMessagePerDay + u2MessagePerDay;
        } catch (SQLException e) {
            logger.error("query Num of Message Per Day error.", e);
        }
        return -1;
    }

    //监控查询状态(单群组)
    public int queryGroupMessagePerDay(long now,int day) {
        try {
            return userGroupDao.queryGroupMessagePerDay(now,day);
        } catch (SQLException e) {
            logger.error("query Num of Message Group Only error.", e);
        }
        return -1;
    }

    //监控查询状态(单个人)
    public int queryU2MessagePerDay(long now,int day) {
        try {
            return messageDao.queryU2MessagePerDay(now,day);
        } catch (SQLException e) {
            logger.error("query Num of Message U2 Only error.", e);
        }
        return -1;

    }

    //监控查询状态(注册人数)
    public int queryNumRegisterPerDay(long now,int day) {
        try {
            return userProfileDao.queryNumRegisterPerDay(now,day);
        } catch (SQLException e) {
            logger.error("query Num of Register Per Day error.", e);
        }
        return -1;
    }

    //查询全部用户数
    public int getSiteUserNum() {
        try {
            return userProfileDao.getUserNum();
        } catch (SQLException e) {
            logger.error("query Num of SiteUser error", e);
        }
        return -1;
    }

    //查询全部群组数量
    public int getGroupNum() {
        try {
            return groupDao.getGroupNum();
        } catch (SQLException e) {
            logger.error("query Num of Group error.", e);
        }
        return -1;
    }


}