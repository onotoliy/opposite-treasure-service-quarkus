package com.github.onotoliy.opposite.treasure;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class JooqDSLContextProducer {

    private final AgroalDataSource ds;

    public JooqDSLContextProducer(AgroalDataSource ds) {
        this.ds = ds;
    }

    @ApplicationScoped
    public DSLContext produceDslContext() {
        return DSL.using(ds, SQLDialect.POSTGRES).dsl();
    }
    
}
