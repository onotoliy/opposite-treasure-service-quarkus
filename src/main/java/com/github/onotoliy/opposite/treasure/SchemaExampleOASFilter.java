package com.github.onotoliy.opposite.treasure;

import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.PathItem;

public class SchemaExampleOASFilter implements OASFilter {

    public PathItem filterPathItem(PathItem pathItem) {

        setNewOperationId(pathItem.getGET(), "GET");
        setNewOperationId(pathItem.getPOST(), "POST");
        setNewOperationId(pathItem.getPUT(), "PUT");
        setNewOperationId(pathItem.getDELETE(), "DELETE");
        setNewOperationId(pathItem.getOPTIONS(), "OPTIONS");
        setNewOperationId(pathItem.getPATCH(), "PATCH");
        setNewOperationId(pathItem.getHEAD(), "HEAD");
        setNewOperationId(pathItem.getTRACE(), "TRACE");

        return pathItem;
    }

    private void setNewOperationId(final Operation operation, final String suffix) {
        if (operation == null) {
            return;
        }

        operation
                .getTags()
                .stream()
                .filter(it -> it.endsWith("Api"))
                .map(it -> it.replaceAll("Api", ""))
                .findFirst()
                .ifPresent(tag -> {
                    final String operationId = operation.getOperationId();

                    operation.setOperationId(operationId + tag + suffix);
                });
    }
}