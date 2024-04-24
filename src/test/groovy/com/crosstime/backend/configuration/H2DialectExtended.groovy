package com.crosstime.backend.configuration

import org.hibernate.dialect.H2Dialect


class H2DialectExtended extends H2Dialect {

    @Override
    String toBooleanValueString(boolean bool) {
        return bool ? "TRUE" : "FALSE";
    }


}