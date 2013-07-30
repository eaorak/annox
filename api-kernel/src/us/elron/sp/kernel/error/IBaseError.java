package us.elron.sp.kernel.error;

public interface IBaseError {

    public int code();

    public String description();

    public ErrorActions action();

}
