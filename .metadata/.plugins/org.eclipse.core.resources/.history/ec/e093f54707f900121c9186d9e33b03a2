package com.adenon.sp.kernel.utils.assist;


public interface IAssistBuilder {

    String NL  = "\n";     // New line
    String NLT = NL + "\t"; // New line and tab

    IAssistBuilder addImport(Class<?>... classes);

    IAssistBuilder addInterface(Class<?>... interfaces) throws Exception;

    IAssistBuilder addField(String initializer) throws Exception;

    IAssistBuilder addMethod(String body) throws Exception;

    IAssistBuilder register(Advice advice,
                            String code);

    IAssistBuilder register(Advice advice,
                            String code,
                            IAdviceListener listener);

    <T> T generate(Class<T> inf) throws Exception;

    Object generate() throws Exception;

    Object getTarget();
}
