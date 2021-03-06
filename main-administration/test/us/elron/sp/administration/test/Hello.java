package us.elron.sp.administration.test;

import us.elron.sp.administration.ConfigLocation;
import us.elron.sp.administration.annotate.MBean;

@MBean(location = ConfigLocation.SYSTEM, subLocation = "myBean=Really-my-bean", id = "id")
public class Hello {

    private String message = null;

    public Hello() {
        this.message = "Hello there";
    }

    public Hello(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String sayHello() {
        return this.message;
    }

    public int getId() {
        return 10;
    }

    @Override
    public String toString() {
        return "Hello [message=" + this.message + "]";
    }

}
