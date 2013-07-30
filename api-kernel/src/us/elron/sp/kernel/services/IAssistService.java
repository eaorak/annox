package us.elron.sp.kernel.services;

import us.elron.sp.kernel.utils.assist.IAssistBuilder;

public interface IAssistService {

    IAssistBuilder createProxyFor(Object target,
                                  boolean extend) throws Exception;

    Object addLoggingTo(IAssistBuilder builder) throws Exception;

}
