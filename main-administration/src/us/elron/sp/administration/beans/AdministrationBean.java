package us.elron.sp.administration.beans;

import us.elron.sp.administration.ConfigLocation;
import us.elron.sp.administration.annotate.MBean;
import us.elron.sp.administration.annotate.Operation;
import us.elron.sp.administration.annotate.Parameter;

@MBean(location = ConfigLocation.SYSTEM, subLocation = "administration=Administration")
public class AdministrationBean {

    @Operation(name = "Import configuration", desc = "Import exported configurations.")
    public String importConfiguration(@Parameter(name = "File path", desc = "Path of configuration file.") String filePath) {
        return "Not implemented yet. Coming soon..";
    }

}
