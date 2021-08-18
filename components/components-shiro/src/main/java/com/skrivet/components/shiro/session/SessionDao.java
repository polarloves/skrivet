package com.skrivet.components.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

import java.io.Serializable;

public class SessionDao extends EnterpriseCacheSessionDAO {
    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        try {
            return super.readSession(sessionId);
        } catch (UnknownSessionException e) {
            return null;
        }
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        try {
            super.update(session);
        } catch (UnknownSessionException e) {
        }
    }
}
