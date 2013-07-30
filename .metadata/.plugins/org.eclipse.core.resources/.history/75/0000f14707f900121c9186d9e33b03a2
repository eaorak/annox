package com.adenon.sp.kernel.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Properties {

    public class Property {

        private final String value;

        public Property(final String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }

        public int toInt() {
            return Integer.parseInt(this.value);
        }

        public long toLong() {
            return Long.parseLong(this.value);
        }

        public double toDouble() {
            return Double.parseDouble(this.value);
        }
    }

    private final String[][] values;
    private Enum<?>          parent;

    public static Properties load(final String filePath,
                                  final IKeyEnum[] parent,
                                  final IKeyEnum[] vals) throws IOException {
        final java.util.Properties properties = new java.util.Properties();
        InputStream is = null;
        try {
            is = new FileInputStream(new File(filePath));
            properties.load(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return new Properties(properties, parent, vals);
    }

    private Properties(final Properties props,
                       final Enum<?> parent) {
        this.values = props.values;
        this.parent = parent;
    }

    public Properties forParent(final Enum<?> parent) {
        return new Properties(this, parent);
    }

    public Property get(final Enum<?> key) {
        if (this.parent == null) {
            throw new RuntimeException("Parent is null. Call forParent(parent) first !");
        }
        return this.get(this.parent, key);
    }

    private Properties(final java.util.Properties props,
                       final IKeyEnum[] parents,
                       final IKeyEnum[] vals) {
        this.values = new String[parents.length][vals.length];
        int i = 0;
        for (final IKeyEnum parent : parents) {
            int j = 0;
            for (final IKeyEnum val : vals) {
                this.values[i][j++] = props.getProperty(parent.key() + "." + val.key());
            }
            i++;
        }
    }

    public Property get(final Enum<?> parent,
                        final Enum<?> key) {
        return new Property(this.values[parent.ordinal()][key.ordinal()]);
    }

    public int toInt(final String value) {
        return Integer.parseInt(value);
    }

}