package database;

import java.sql.Date;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class DBValue<T> {
    private String title;
    private T value;
    private T defaultValue;

    private JDBCType type;
    private int size = 0;

    private boolean isPrimary = false;
    private boolean isNotNull = false;
    private  boolean isAutoIncrement = false;

    private IDBEntityFactory foreignKeyFactory;

    public DBValue(String title, T value, JDBCType type) {
        this.title = title;
        this.value = value;
        this.type = type;
    }

    public DBValue(String title, JDBCType type) {
        this.title = title;
        this.type = type;
    }

    public JDBCType getType() {
        return type;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public boolean isForeignKey() {
        return this.foreignKeyFactory != null;
    }

    public IDBEntityFactory getForeignKeyFactory() {
        return foreignKeyFactory;
    }

    public String inQuotes() {
        T finalValue = value == null && isNotNull() ? defaultValue : value;
        if(type == JDBCType.DATE)
            return DBValue.coverByQuotes(new SimpleDateFormat("yyyy-MM-dd").format(finalValue));
        return DBValue.coverByQuotes(finalValue);
    }

    public String forSet() {
        T finalValue = value == null && isNotNull() ? defaultValue : value;
        return title+"="+DBValue.coverByQuotes(finalValue);
    }

    public DBValue addSize(int size) {
        this.size = size;
        return this;
    }

    public DBValue addPrimaryKey() {
        this.isPrimary = true;
        return this;
    }

    public DBValue addForeignKey(IDBEntityFactory factory) {
        this.foreignKeyFactory = factory;
        return this;
    }

    public DBValue addDefaultValue(T value) {
        this.defaultValue = value;
        return this;
    }

    public DBValue addNotNull() {
        this.isNotNull = true;
        return this;
    }

    public DBValue addAutoIncrement() {
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

        if(this.foreignKeyFactory!=null)
            result += String.format(", FOREIGN KEY (%s) REFERENCES %s(%s)",
                    title, foreignKeyFactory.create().getTableID(),
                    foreignKeyFactory.create().getEntityID().getTitle());

        return result;
    }

    public T getValue() {
        return value;
    }

    public static String coverByQuotes(Object value) {
        return "'" + value.toString() + "'";
    }

    public String getTitle() {
        return title;
    }
}
