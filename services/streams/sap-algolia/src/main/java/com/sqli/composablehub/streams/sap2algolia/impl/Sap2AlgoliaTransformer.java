package com.sqli.composablehub.streams.sap2algolia.impl;

import com.sqli.composablehub.algolia.models.ArticleTarget;
import com.sqli.composablehub.sap.models.Category;
import com.sqli.composablehub.sap.models.SAPProductRaw;
import com.sqli.composablehub.streamscore.transformation.StreamTransformer;

import java.util.ArrayList;
import java.util.List;

public class Sap2AlgoliaTransformer implements StreamTransformer<SAPProductRaw,ArticleTarget> {


    @Override
    public ArticleTarget transform(SAPProductRaw source) {
        ArticleTarget target = new ArticleTarget();
        target.setCode(source.getCode());
        target.setObjectID(source.getCode().toString() + "_" + source.getCatalog() + "_" + source.getCatalogVersion());
        target.setCatalogVersion(source.getCatalog() + "_" + source.getCatalogVersion());
        target.setIntegrationKey(source.getCode().toString() + "_" + source.getCatalog() + "_" + source.getCatalogVersion());

        List<CharSequence> charSeqList = new ArrayList<>();
        for (Category cat : source.getSupercategories()) {
            charSeqList.add(cat.getCode().toString());
        }
        target.setSupercategories(charSeqList);
        return target;

    }

}
