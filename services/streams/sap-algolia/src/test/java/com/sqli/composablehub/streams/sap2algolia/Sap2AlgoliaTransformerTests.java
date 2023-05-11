package com.sqli.composablehub.streams.sap2algolia;

import com.sqli.composablehub.algolia.models.ArticleTarget;
import com.sqli.composablehub.sap.models.Category;
import com.sqli.composablehub.sap.models.SAPProductRaw;
import com.sqli.composablehub.streams.sap2algolia.impl.Sap2AlgoliaTransformer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Sap2AlgoliaTransformerTests {
    //generate unit tests for Sap2AlgoliaTransformer
    @Test
    public void testTransform() {
            Sap2AlgoliaTransformer transformer = new Sap2AlgoliaTransformer();

            SAPProductRaw source = new SAPProductRaw();
            source.setCode("12345");
            source.setCatalog("mycatalog");
            source.setCatalogVersion("1.0");
            Category cat1 = new Category();
            cat1.setCode("cat1");
            Category cat2 = new Category();
            cat2.setCode("cat2");
            List<Category> categories = new ArrayList<>();
            categories.add(cat1);
            categories.add(cat2);
            source.setSupercategories(categories);

            ArticleTarget target = transformer.transform(source);

            assertEquals("12345", target.getCode());
            assertEquals("12345_mycatalog_1.0", target.getObjectID());
            assertEquals("mycatalog_1.0", target.getCatalogVersion());
            assertEquals("12345_mycatalog_1.0", target.getIntegrationKey());
            List<CharSequence> supercategories = target.getSupercategories();
            assertEquals(2, supercategories.size());
            assertEquals("cat1", supercategories.get(0).toString());
            assertEquals("cat2", supercategories.get(1).toString());
        }

}
