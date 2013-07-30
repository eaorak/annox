package com.adenon.sp.kernel.dialog;

import java.util.Collection;

/**
 * Don't use thic class directly. use either IEnablerDialogCache or IServiceDialogCache
 */

public interface IDialogCache {

    IDialog getDialog(String protocolId) throws Exception;

    void putDialog(IDialog dialog);

    void removeDialog(IDialog dialog);

    Collection<IDialog> getDialogs();

    void clear();

}
