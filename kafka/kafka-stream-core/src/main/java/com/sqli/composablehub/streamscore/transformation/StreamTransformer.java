package com.sqli.composablehub.streamscore.transformation;

import org.apache.avro.specific.SpecificRecordBase;

public interface StreamTransformer<S extends SpecificRecordBase, T extends SpecificRecordBase> {
    T transform(S source);
}
