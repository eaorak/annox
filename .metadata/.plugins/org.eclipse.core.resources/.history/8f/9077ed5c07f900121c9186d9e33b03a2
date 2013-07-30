package com.adenon.sp.administration;

public enum Primitives {

    BYTE(Byte.TYPE, Byte.class), //
    SHORT(Short.TYPE, Short.class), //
    INTEGER(Integer.TYPE, Integer.class), //
    LONG(Long.TYPE, Long.class), //
    FLOAT(Float.TYPE, Float.class), //
    DOUBLE(Double.TYPE, Double.class), //
    BOOLEAN(Boolean.TYPE, Boolean.class), //
    CHAR(Character.TYPE, Character.class), //
    STRING(null, String.class);

    private Class<?> primitive;
    private Class<?> wrapper;

    private Primitives(Class<?> primitive,
                       Class<?> wrapper) {
        this.primitive = primitive;
        this.wrapper = wrapper;
    }

    @SuppressWarnings("unchecked")
    public <T> T parse(String value) throws Exception {
        switch (this) {
            case BYTE:
                return (T) (Byte) Byte.parseByte(value);
            case SHORT:
                return (T) (Short) Short.parseShort(value);
            case INTEGER:
                return (T) (Integer) Integer.parseInt(value);
            case LONG:
                return (T) (Long) Long.parseLong(value);
            case FLOAT:
                return (T) (Float) Float.parseFloat(value);
            case DOUBLE:
                return (T) (Double) Double.parseDouble(value);
            case BOOLEAN:
                return (T) (Boolean) Boolean.parseBoolean(value);
            case CHAR:
                if (value.length() > 1) {
                    break;
                }
                return (T) (Character) value.charAt(0);
            case STRING:
                return (T) value;
        }
        throw new Exception("Invalid value [" + value + "] for type [" + this + "] !");
    }

    public static Primitives getTypeOf(Class<?> c) {
        return getTypeOf(c, false);
    }

    public static Primitives getTypeOf(Class<?> c,
                                       boolean stringIncluded) {
        if (c == String.class) {
            return stringIncluded ? STRING : null;
        }
        for (Primitives p : Primitives.values()) {
            if (c.equals(p.primitive) || c.equals(p.wrapper)) {
                return p;
            }
        }
        return null;
    }

    public static boolean isPrimitive(Class<?> c,
                                      boolean stringIncluded) {
        return getTypeOf(c, stringIncluded) != null;
    }

    public Class<?> getPrimitive() {
        return this.primitive;
    }

    public Class<?> getWrapper() {
        return this.wrapper;
    }

}
