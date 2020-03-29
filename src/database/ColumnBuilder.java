package database;

import java.sql.JDBCType;

public class ColumnBuilder<T> {
    String title;
    T value;
    T defaultValue;
    JDBCType type;
    int size = 0;

    boolean isPrimary = false;
    boolean isNotNull = false;
    boolean isAutoIncrement = false;

    String foreignKey;

    public ColumnBuilder(String title, T value, JDBCType type) {
        this.title = title;
        this.value = value;
        this.type = type;
    }

    public ColumnBuilder(String title, JDBCType type) {
        this.title = title;
        this.type = type;
    }

    public ColumnBuilder addSize(int size) {
        this.size = size;
        return this;
    }

    public ColumnBuilder addPrimaryKey() {
        this.isPrimary = true;
        return this;
    }

    public ColumnBuilder addForeignKey(String tableID, String keyID) {
        this.foreignKey = String.format("%s(%s)", tableID, keyID);
        return this;
    }

    public ColumnBuilder addDefaultValue(T value) {
        this.defaultValue = value;
        return this;
    }

    public ColumnBuilder addNotNull() {
        this.isNotNull = true;
        return this;
    }

    public ColumnBuilder addAutoIncrement() {
        this.isAutoIncrement = true;
        return this;
    }

    /**
     * @return build string for a column view of this variable
     */
    public String build() {
        String result = this.title;
        result += " " + this.type.getName();
        if(this.size != 0)
            result += String.format("(%s)", this.size);

        if(this.isPrimary)
            result += " PRIMARY KEY";

        if(this.isNotNull)
            result += " NOT NULL";

        if(this.isAutoIncrement)
            result += " AUTO_INCREMENT";

        if(this.defaultValue!=null)
            result += String.format(" DEFAULT '%s'", defaultValue);

        if(this.foreignKey!=null)
            result += String.format(", FOREIGN KEY (%s) REFERENCES %s", title, foreignKey);

        return result;
    }

    /**
     * @return return value as string
     */
    public String get() {
        return "'" + (String)value + "'";
    }

    public static String coverByQuotes(Object value) {
        return "'" + (String)value + "'";
    }
}
