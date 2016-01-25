package com.nikita.pischik.orderlunch.hibernate.dialect;


import org.hibernate.dialect.SQLServerDialect;

import java.sql.Types;

public class MySQLServerDialect extends SQLServerDialect{

    public MySQLServerDialect() {
        super();
        registerColumnType(Types.VARCHAR, "nvarchar($l)");
    }
}
