package com.sqli.composablehub.streams.sap2algolia.transformation;

import org.apache.avro.specific.SpecificRecordBase;

public interface StreamTransformer<S extends SpecificRecordBase, T extends SpecificRecordBase> {
    T transform(S source);
}
