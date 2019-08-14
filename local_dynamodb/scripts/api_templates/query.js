var params = {
    TableName: 'table_name',
    IndexName: 'index_name', // optional (if querying an index)
    KeyConditionExpression: 'attribute_name = :value', // a string representing a constraint on the attribute
    FilterExpression: 'attr_name = :val', // a string representing a constraint on the attribute
    ExpressionAttributeNames: { // a map of substitutions for attribute names with special characters
        //'#name': 'attribute name'
    },
    ExpressionAttributeValues: { // a map of substitutions for all attribute values
        ':value': 'STRING_VALUE',
        ':val': 0
    },
    ScanIndexForward: true, // optional (true | false) defines direction of Query in the index
    Limit: 0, // optional (limit the number of items to evaluate)
    ConsistentRead: false, // optional (true | false)
    Select: 'ALL_ATTRIBUTES', // optional (ALL_ATTRIBUTES | ALL_PROJECTED_ATTRIBUTES |
                              //           SPECIFIC_ATTRIBUTES | COUNT)
    AttributesToGet: [ // optional (list of specific attribute names to return)
        'attribute_name',
        // ... more attributes ...
    ],
    ExclusiveStartKey: { // optional (for pagination, returned by prior calls as LastEvaluatedKey)
        attribute_name: attribute_value,
        // attribute_value (string | number | boolean | null | Binary | DynamoDBSet | Array | Object)
        // anotherKey: ...

    },
    ReturnConsumedCapacity: 'NONE', // optional (NONE | TOTAL | INDEXES)
};
docClient.query(params, function(err, data) {
    if (err) ppJson(err); // an error occurred
    else ppJson(data); // successful response
});