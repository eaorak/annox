package com.adenon.sp.kernel.event.message.rule;

import com.adenon.sp.kernel.event.Event;


public interface IDispatchRule {

    public EDispatchRuleResult executeRule(Event event) throws Exception;

    public String getMessageName();

    public void setMessageName(String messageName);

    public void registerBeans(Object configuration) throws Exception;

    public void setUniqueId(String uniqueId);

    public String getUniqueId();

    public int getIndex();

    public boolean getActive();
}
