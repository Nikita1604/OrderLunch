package com.nikita.pischik.orderlunch.hibernate.dialect;


import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.SQLServerDialect;

import java.sql.Types;

public class MySQLServerDialect extends MySQL5Dialect{

    public MySQLServerDialect() {
        super();
        //registerHibernateType(Types.NVARCHAR, 4000, "string");
        registerColumnType(Types.VARCHAR, "nvarchar($l)");
    }
}
